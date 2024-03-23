package edu.java.scrapper.core.dao.jpa;

import edu.java.scrapper.core.dao.jpa.entity.JpaLink;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLinkDao extends JpaRepository<JpaLink, Long> {

    Optional<JpaLink> findByLvalue(String lvalue);

    List<JpaLink> findAllByOrderByLastUpdateAtDesc(Pageable pageable);

    default List<JpaLink> getLinksSortedByLastUpdate(int pageSize) {
        Pageable pageable = PageRequest.of(
            0,
            pageSize, Sort.by(Sort.Direction.DESC, "lastUpdateAt")
        );
        return findAllByOrderByLastUpdateAtDesc(pageable);
    }

}
