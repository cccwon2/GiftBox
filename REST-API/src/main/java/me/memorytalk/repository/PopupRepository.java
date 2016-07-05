package me.memorytalk.repository;

import me.memorytalk.domain.Popup;
import me.memorytalk.repository.custom.PopupModelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PopupRepository extends JpaRepository<Popup, Long>,
        JpaSpecificationExecutor<Popup>,
        PopupModelRepository {

        Popup findById(Long id);
}
