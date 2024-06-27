package filters;

import com.t2m.library.entities.Category;
import com.t2m.library.entities.Domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class KnowledgeFilter {
    private String title;
    private Set<Category> categories;
    private Set<Domain> domains;
    private Boolean active;
    private Boolean needsReview;


    public KnowledgeFilter(String title, String category, String domain, Boolean active, Boolean needsReview) {
        HashSet<Category> categories = new HashSet<Category>();
        if (category != null && !category.isEmpty() && !category.isBlank()) {
            categories.add(new Category(category));
        }
        HashSet<Domain> domains = new HashSet<Domain>();
        if (domain != null && !domain.isEmpty() && !domain.isBlank()) {
            domains.add(new Domain(domain));
        }
        this.title = title;
        this.categories = categories;
        this.domains = domains;
        this.active = active;
        this.needsReview = needsReview;
    }

    public Optional<String> getTitle() {
        return Optional.ofNullable(title);
    }

    public Optional<Set<Category>> getCategories() {
        return Objects.nonNull(categories) && !categories.isEmpty()
                ? Optional.of(categories)
                : Optional.empty();
    }

    public Optional<Set<Domain>> getDomains() {
        return Objects.nonNull(domains) && !domains.isEmpty()
                ? Optional.of(domains)
                : Optional.empty();
    }

    public Optional<Boolean> getActive() {
        return Optional.ofNullable(active);
    }

    public Optional<Boolean> getNeedsReview() {
        return Optional.ofNullable(needsReview);
    }
}
