package com.dreddi.android.githublist.data.repository

import com.dreddi.android.githublist.data.entity.RepoData
import com.dreddi.android.githublist.data.entity.RepoListData
import com.dreddi.android.githublist.data.entity.RepoOwnerData
import com.dreddi.android.githublist.data.mapper.RepoDataToEntityMapper
import com.dreddi.android.githublist.data.mapper.RepoListDataToEntityMapper
import com.dreddi.android.githublist.data.mapper.RepoOwnerDataToEntityMapper
import com.dreddi.android.githublist.data.repository.store.RepoStore
import com.dreddi.android.githublist.domain.entity.RepoListEntity
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Assert
import org.junit.Test

class RepoRepositoryImplTest {

    @Test
    fun `repo store should return repositories`() {

        // given
        val repoStore = getRepoStore()

        val repoRepositoryImpl = RepoRepositoryImpl(repoStore,
                RepoListDataToEntityMapper(
                        RepoDataToEntityMapper(RepoOwnerDataToEntityMapper())))

        val testObserver = TestObserver<RepoListEntity>()

        // when
        repoRepositoryImpl.getTopRepositories(0, 1)
                .subscribe(testObserver)

        // then
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val repoListEntity = testObserver.values()[0]

        Assert.assertNotNull(repoListEntity)
        Assert.assertEquals(repoListEntity.totalCount, 1)
        Assert.assertNotNull(repoListEntity.repoDataItemsList)
        Assert.assertEquals(repoListEntity.repoDataItemsList?.size, 1)

        val repoEntity = repoListEntity.repoDataItemsList!![0]

        Assert.assertNotNull(repoEntity)
        Assert.assertEquals(repoEntity.id, 1L)
        Assert.assertEquals(repoEntity.name, "test")
        Assert.assertNotNull(repoEntity.owner)
    }

    private fun getRepoStore(): RepoStore {

        val repoOwner = RepoOwnerData(1, "login", null, null, null, null, null,
                null, null, null, null, null, null, null,
                null, null, null, false)

        val repo = RepoData(1, "test", null, null, null, null, null, null,
                null, null,null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                repoOwner)

        val repoDataItemsList = mutableListOf<RepoData>().apply {
            add(repo)
        }

        val repoListData = RepoListData(1, true, repoDataItemsList)

        return object: RepoStore {
            override fun getTopRepositories(page: Int, perPage: Int): Observable<RepoListData> {
                return Observable.just(repoListData)
            }
        }
    }
}
