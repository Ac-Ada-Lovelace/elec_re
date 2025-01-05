package com.zys.elec.service;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.dto.UserDTO;
import com.zys.elec.entity.Follow;
import com.zys.elec.entity.User;

import java.util.List;

public interface FollowService {
    ServiceResult<Follow> followUser(User follower, User followee);

    ServiceResult<Void> unfollowUser(User follower, User followee);

    ServiceResult<List<Follow>> getFollowers(User followee);

    ServiceResult<List<Follow>> getFollowees(User follower);

    ServiceResult<Boolean> isFollowing(User follower, User followee);

    ServiceResult<List<UserDTO>> getFollowers(long userId);

    ServiceResult<List<UserDTO>> getFollowees(long userId);
    
    ServiceResult<Follow> followUser(long followerId, long followeeId);

    ServiceResult<Void> unfollowUser(long followerId, long followeeId);
}
