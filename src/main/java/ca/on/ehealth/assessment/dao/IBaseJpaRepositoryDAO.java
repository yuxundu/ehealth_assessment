package ca.on.ehealth.assessment.dao;

import ca.on.ehealth.assessment.model.entity.BaseDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

/**
 * Basic dao extend JPA repository <T extends BaseDO>
 * @author yuxundu
 */
@NoRepositoryBean
public interface IBaseJpaRepositoryDAO <T extends BaseDo> extends JpaRepository<T, Long> {
}
