package com.dreddi.android.githublist.data.mapper

import com.dreddi.android.githublist.data.entity.RepoOwnerData
import com.dreddi.android.githublist.domain.entity.RepoOwnerEntity

class RepoOwnerDataToEntityMapper {

    fun mapFrom(repoOwnerData: RepoOwnerData?): RepoOwnerEntity {
        return RepoOwnerEntity(
                repoOwnerData?.id,
                repoOwnerData?.login,
                repoOwnerData?.nodeId,
                repoOwnerData?.avatarUrl,
                repoOwnerData?.gravatarId,
                repoOwnerData?.url,
                repoOwnerData?.htmlUrl,
                repoOwnerData?.followersUrl,
                repoOwnerData?.followingUrl,
                repoOwnerData?.gistsUrl,
                repoOwnerData?.starredUrl,
                repoOwnerData?.subscriptionsUrl,
                repoOwnerData?.organizationsUrl,
                repoOwnerData?.reposUrl,
                repoOwnerData?.eventsUrl,
                repoOwnerData?.receivedEventsUrl,
                repoOwnerData?.type,
                repoOwnerData?.isSiteAdmin
        )
    }
}