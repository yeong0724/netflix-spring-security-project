package com.jinyeong.netflix.user;

import com.jinyeong.netflix.user.command.UserHistoryCommand;

public interface UserHistoryUseCase {
    void createHistory(UserHistoryCommand userHistoryCommand);
}
