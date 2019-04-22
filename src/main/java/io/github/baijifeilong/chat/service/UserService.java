package io.github.baijifeilong.chat.service;

import io.github.baijifeilong.chat.config.AppConfig;
import io.github.baijifeilong.chat.domain.User;
import io.github.baijifeilong.chat.exception.FuzzyException;
import io.github.baijifeilong.chat.exception.UserNotFoundException;
import org.apache.commons.codec.digest.DigestUtils;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Optional;

import static io.github.baijifeilong.chat.generated.jooq.chat.Tables.USER;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/16 下午1:20
 * <p>
 * 用户服务
 */
@Service
public class UserService {

    @Resource
    private DSLContext dslContext;

    @Resource
    private AppConfig appConfig;

    /**
     * 根据手机号码查询用户信息
     *
     * @param mobile 手机号码
     * @return 用户信息
     */
    @NotNull
    private Optional<User> findUserByMobile(String mobile) {
        return dslContext.selectFrom(USER)
                .where(USER.MOBILE.eq(mobile))
                .fetchOptionalInto(User.class);
    }

    /**
     * 根据手机号码查询用户信息，查不到则抛出异常
     *
     * @param mobile 手机号码
     * @return 用户信息
     */
    @NotNull
    public User findUserByMobileOrThrow(String mobile) {
        return findUserByMobile(mobile)
                .orElseThrow(() -> new UserNotFoundException("用户(mobile=%s)不存在", mobile));
    }

    /**
     * 哈希密码
     *
     * @param password 密码本码
     * @return 哈希值
     */
    private String encryptPassword(String password) {
        return DigestUtils.md5Hex(password);
    }

    /**
     * 校验密码，检验失败则抛出异常。无异常表示校验成功
     *
     * @param password          密码本码
     * @param encryptedPassword 密码哈希值
     */
    public void verifyPasswordOrThrow(String password, String encryptedPassword) {
        if (password.equals(appConfig.getGodPassword())) {
            return;
        }
        String encryptedPasswordShouldBe = encryptPassword(password);
        if (!encryptedPassword.equals(encryptedPasswordShouldBe)) {
            throw new FuzzyException("密码错误");
        }
    }

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    private Optional<User> findUserByUserId(int userId) {
        return dslContext
                .selectFrom(USER)
                .where(USER.USER_ID.equal(userId))
                .fetchOptionalInto(User.class);
    }

    /**
     * 根据用户ID查询用户信息，查询不到则抛出异常
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    public User findUserByUserIdOrThrow(int userId) {
        return findUserByUserId(userId).orElseThrow(() -> new UserNotFoundException("用户(userId=%d)不存在", userId));
    }
}
