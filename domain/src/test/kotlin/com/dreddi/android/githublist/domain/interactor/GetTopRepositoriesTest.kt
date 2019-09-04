package com.dreddi.android.githublist.domain.interactor

import com.dreddi.android.githublist.domain.RepoRepository
import com.dreddi.android.githublist.domain.entity.RepoEntity
import com.dreddi.android.githublist.domain.entity.RepoListEntity
import com.dreddi.android.githublist.domain.entity.RepoOwnerEntity
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Assert.*
import org.junit.Test

class GetTopRepositoriesTest {

    @Test
    fun getTopRepositories_getRepositories() {

        var repoRepository = getTestRepoRepository()
        val testObserver = TestObserver<RepoListEntity>()

        repoRepository.getTopRepositories(0, 1)
                .subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        var repoListEntity = testObserver.values()[0];

        assertNotNull(repoListEntity)
        assertEquals(repoListEntity.totalCount, 1)
        assertNotNull(repoListEntity.repoDataItemsList)
        assertEquals(repoListEntity.repoDataItemsList?.size, 1)

        var repoEntity = repoListEntity.repoDataItemsList!![0]

        assertNotNull(repoEntity)
        assertEquals(repoEntity.id, 1L)
        assertEquals(repoEntity.name, "test")
        assertNotNull(repoEntity.owner)
    }

    fun getTestRepoRepository(): RepoRepository {

        var repoOwner = RepoOwnerEntity(1, "login", null, null, null, null, null,
                null, null, null, null, null, null, null,
                null, null, null, false)

        var repo = RepoEntity(1, "test", null, null, null, null, null, null,
                null, null,null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                repoOwner)

        var repoDataItemsList: MutableList<RepoEntity> = mutableListOf<RepoEntity>()
        repoDataItemsList.add(repo);

        var repoListEntity: RepoListEntity =
                RepoListEntity(1, true, repoDataItemsList)

        return object: RepoRepository {
            override fun getTopRepositories(page: Int, perPage: Int): Observable<RepoListEntity> {
                    return Observable.just(repoListEntity)
            }
        }
    }
}