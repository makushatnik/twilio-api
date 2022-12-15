package com.example.myproj.service;

import static com.example.myproj.enums.PostRate.DISLIKE;
import static com.example.myproj.enums.PostState.ARCHIVED;
import static com.example.myproj.util.PagedUtils.toPaged;
import static java.util.Objects.isNull;

import com.example.myproj.dto.chat.MessageRequest;
import com.example.myproj.dto.chat.MessageResponse;
import com.example.myproj.dto.chat.ThreadDto;
import com.example.myproj.dto.page.Paged;
import com.example.myproj.mapper.ThreadMapper;
import com.example.myproj.model.Thread;
import com.example.myproj.model.User;
import com.example.myproj.model.post.Post;
import com.example.myproj.model.post.PostLike;
import com.example.myproj.repository.ThreadRepository;
import com.example.myproj.repository.post.PostLikeRepository;
import com.twilio.Twilio;
import com.twilio.rest.conversations.v1.Conversation;
import com.twilio.rest.conversations.v1.conversation.Message;
import com.twilio.rest.conversations.v1.conversation.Participant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThreadService {

    private static final String STARTING_CONVERSATION_WITH_BUYER_ERROR = "You shouldn't start conversation with a buyer";
    private static final String MESSAGE_ABOUT_ARCHIVED_POST_ERROR = "You can't write a message about an archived post";
    private static final String CREATING_THREAD_WITHOUT_LIKE_ERROR = "You can't write a message by post you hadn't liked";
    private static final String CREATING_THREAD_DISLIKE_ERROR = "You can't write a message by post you disliked";

    private static final String TWILIO_CONVERSATIONS_URL = "https://conversations.twilio.com/v1/Conversations/";
    private static final String MESSAGES = "/Messages?";
    private static final String PAGE_TOKEN = "PageToken";
    private static final String PAGE_SIZE = "PageSize";
    private static final String PAGE = "Page";

    private final ThreadMapper mapper;
    private final ThreadRepository threadRepository;
    private final UserService userService;
    private final PostService postService;
    private final PostLikeRepository likeRepository;

    @Value("${app.twilio.account-sid}")
    private String accountSid;

    @Value("${app.twilio.auth-token}")
    private String authToken;

    @Value("${app.twilio.phone-number}")
    private String phoneNumber;

    public Paged<ThreadDto> getThreadList(Long userId, Pageable pageable) {
        userService.findById(userId);
        return toPaged(threadRepository.findAllByBuyerIdOrSellerId(userId, pageable)
                               .map(mapper::toDto));
    }

    public Paged<MessageResponse> getMessages(Long id, Integer page, Integer size) {
        Thread thread = threadRepository.findById(id)
                                        .orElseThrow(EntityNotFoundException::new);

        String pageToken = page == 0 ? "" : "&" + PAGE_TOKEN + "=PT" + (size * page);
        Twilio.init(accountSid, authToken);
        List<Message> messages = Message.reader(thread.getSid())
                .pageSize(size)
                .getPage(TWILIO_CONVERSATIONS_URL + thread.getSid() + MESSAGES +
                        PAGE_SIZE + "=" + size + "&" + PAGE + "=" + page + pageToken)
                .getRecords();

        Long postId = thread.getPost().getId();
        List<MessageResponse> result = new ArrayList<>();
        for (Message message : messages) {
            MessageResponse resp = mapper.toMessageResponse(message);
            resp.setPostId(postId);
            result.add(resp);
        }

        return toPaged(result);
    }

    @Transactional
    public MessageResponse send(MessageRequest dto, Long currentUserId) {
        User currentUser = userService.findById(currentUserId);
        User anotherUser = userService.findById(dto.getUserId());
        Post post = postService.findById(dto.getPostId());
        if (post.getState() == ARCHIVED) {
            throw new IllegalArgumentException(MESSAGE_ABOUT_ARCHIVED_POST_ERROR);
        }

        String threadSid;
        Twilio.init(accountSid, authToken);
        //in case of started dialog
        Optional<Thread> threadOpt = threadRepository.findByBuyerIdAndSellerIdAndPostId(
                currentUserId, dto.getUserId(), dto.getPostId());
        //searching vice versa
        if (threadOpt.isEmpty()) {
            threadOpt = threadRepository.findByBuyerIdAndSellerIdAndPostId(
                    dto.getUserId(), currentUserId, dto.getPostId());
        }

        if (threadOpt.isEmpty()) {
            creationThreadChecks(post, currentUserId);
            threadSid = createThread(post, currentUser, anotherUser, dto);
            increaseThreads(post);
        } else {
            threadSid = threadOpt.get().getSid();
        }

        Message message = Message.creator(threadSid)
                .setAuthor(currentUser.getFirstName())
                .setBody(dto.getText())
                .create();

        increaseMessages(post);
        postService.save(post);

        MessageResponse resp = mapper.toMessageResponse(message);
        resp.setPostId(post.getId());
        return resp;
    }

    @Transactional
    public void report(Long userId, String complaint) {
        userService.report(userId, complaint);
    }

    @Transactional
    public void delete(Long id) {
        Thread thread = threadRepository.findById(id)
                                        .orElseThrow(EntityNotFoundException::new);

        Twilio.init(accountSid, authToken);
        Conversation.deleter(thread.getSid())
                    .delete();
        threadRepository.delete(thread);
    }

    @Transactional
    public void delete(String sid) {
        Optional<Thread> threadOpt = threadRepository.findBySid(sid);

        Twilio.init(accountSid, authToken);
        Conversation.deleter(sid)
                    .delete();
        threadOpt.ifPresent(threadRepository::delete);
    }

    private void increaseThreads(Post post) {
        int count = isNull(post.getThreadCount()) ? 0 : post.getThreadCount();
        post.setThreadCount(++count);
    }

    private void increaseMessages(Post post) {
        int count = isNull(post.getMessageCount()) ? 0 : post.getMessageCount();
        post.setMessageCount(++count);
    }

    private void creationThreadChecks(Post post, Long currentUserId) {
        if (post.getUser().getId().equals(currentUserId)) {
            throw new IllegalArgumentException(STARTING_CONVERSATION_WITH_BUYER_ERROR);
        }

        PostLike like = likeRepository.findByPostIdAndUserId(post.getId(), currentUserId);
        if (isNull(like) || isNull(like.getRate())) {
            log.error(CREATING_THREAD_WITHOUT_LIKE_ERROR);
            throw new IllegalStateException(CREATING_THREAD_WITHOUT_LIKE_ERROR);
        }
        if (like.getRate() == DISLIKE) {
            log.error(CREATING_THREAD_DISLIKE_ERROR);
            throw new IllegalStateException(CREATING_THREAD_DISLIKE_ERROR);
        }
    }

    private String createThread(Post post, User current, User another, MessageRequest dto) {
        Conversation conversation = Conversation.creator()
                .setFriendlyName(current.getPhoneNumber() + " - " + another.getPhoneNumber() + " - "
                        + dto.getPostId())
                .create();
        String threadSid = conversation.getSid();

//            Participant.creator(threadSid)
//                       .setMessagingBindingAddress(currentUser.getPhoneNumber())
//                       .setMessagingBindingProxyAddress(phoneNumber)
//                       .create();
//            Participant.creator(threadSid)
//                       .setMessagingBindingAddress(anotherUser.getPhoneNumber())
//                       .setMessagingBindingProxyAddress(phoneNumber)
//                       .create();
        Participant.creator(threadSid)
                .setIdentity(current.getPhoneNumber())
                .create();
        Participant.creator(threadSid)
                .setIdentity(another.getPhoneNumber())
                .create();

        Thread newThread = new Thread()
                .setSid(threadSid)
                .setBuyer(current)
                .setSeller(another)
                .setPost(post);
        threadRepository.save(newThread);
        return threadSid;
    }
}
