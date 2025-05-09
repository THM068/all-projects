package com.blog.learning.repository.persistence;

import com.blog.learning.repository.model.Post;
import com.blog.learning.repository.model.common.Page;
import com.blog.learning.repository.model.common.PaginationOptions;
import com.blog.learning.repository.persistence.common.FaunaRepository;
import com.faunadb.client.query.Pagination;

import jakarta.inject.Singleton;

import java.util.concurrent.CompletableFuture;

import static com.faunadb.client.query.Language.*;

/**
 * {@link FaunaRepository} implementation for the {@link Post} entity.
 */
@Singleton
public class PostRepository extends FaunaRepository<Post> {

    public PostRepository() {
        super(Post.class, "posts", "all_posts");
    }

    //-- Custom repository operations specific to the current entity go below --//
    /**
     * It finds all Posts matching the given title.
     *
     * @param title title to find Posts by
     * @param po the {@link PaginationOptions} to determine which {@link Page} of results to return
     * @return a {@link Page} of {@link Post} entities
     */
    public CompletableFuture<Page<Post>> findByTitle(String title, PaginationOptions po) {
        Pagination paginationQuery = Paginate(Match(Index(Value("posts_by_title")), Value(title)));
        po.getSize().ifPresent(size -> paginationQuery.size(size));
        po.getAfter().ifPresent(after -> paginationQuery.after(Ref(Class(className), Value(after))));
        po.getBefore().ifPresent(before -> paginationQuery.before(Ref(Class(className), Value(before))));

        CompletableFuture<Page<Post>> result =
            client.query(
                Map(
                    paginationQuery,
                    Lambda(Value("nextRef"), Select(Value("data"), Get(Var("nextRef"))))
                )
            )
            .thenApply(this::toPage);

        return result;
    }

}
