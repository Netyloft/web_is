package com.example.jpa;

import com.example.api.resolver.filter.FilterParameters;
import com.example.exception.BadRequestException;
import com.example.exception.ConflictException;
import com.example.exception.NotFoundException;
import com.example.jpa.dao.BaseCrudDao;
import com.example.jpa.entity.BaseEntity;
import com.example.jpa.mapper.BaseEntityMapper;
import com.example.jpa.utils.SpecificationHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

public abstract class AbstractCrudRepository<
    D extends BaseDto,
    E extends BaseEntity,
    Dao extends BaseCrudDao<E>,
    M extends BaseEntityMapper<D, E>
    > extends AbstractReadRepository<D, Long, E, Dao, M> implements BaseCrudRepository<D> {

    private final Class<E> classEntity;

    @PersistenceContext
    private EntityManager entityManager;

    public AbstractCrudRepository(Dao dao, M mapper, Class<E> classEntity) {
        super(dao, mapper);
        this.classEntity = classEntity;
    }

    @Override
    public D create(D model) {
        try {
            E saved = dao.save(mapper.toEntity(model));
            return mapper.toDto(saved);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new ConflictException("Конфликт при создании записи", dataIntegrityViolationException);
        } catch (DataAccessException dataAccessException) {
            throw new BadRequestException("Невозможно создать запись", dataAccessException);
        }
    }

    @Override
    public List<D> create(List<D> update) {
        try {
            List<E> savingEntities = mapper.toEntity(update);
            List<E> savedEntities = dao.saveAll(savingEntities);
            return mapper.toDto(savedEntities);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new ConflictException("Конфликт при создании записи", dataIntegrityViolationException);
        } catch (DataAccessException dataAccessException) {
            throw new BadRequestException("Невозможно создать запись", dataAccessException);
        }
    }

    @Override
    public D update(D model) {
        try {
            E entity = dao.findById(model.getId()).orElse(null);

            if (entity == null) {
                return null;
            }

            E entityForUpdate = mapper.toEntity(model);
            E updatedEntity = dao.save(entityForUpdate);

            if (updatedEntity == null) {
                throw new NotFoundException("Запись не найдена");
            }

            return mapper.toDto(updatedEntity);
        } catch (DataIntegrityViolationException | ConstraintViolationException dataIntegrityViolationException) {
            throw new ConflictException("Конфликт при обновлении записи", dataIntegrityViolationException);
        } catch (DataAccessException dataAccessException) {
            throw new BadRequestException("Невозможно выполнить операцию обновления", dataAccessException);
        }
    }

    @Override
    public List<D> update(List<D> update) {
        try {
            List<E> updatingEntities = mapper.toEntity(update);
            List<E> updatedEntities = dao.saveAll(updatingEntities);

            if (updatedEntities.isEmpty()) {
                throw new NotFoundException("Запись не найдена");
            }

            return mapper.toDto(updatedEntities);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new ConflictException("Конфликт при обновлении записи", dataIntegrityViolationException);
        } catch (DataAccessException dataAccessException) {
            throw new BadRequestException("Невозможно выполнить операцию обновления", dataAccessException);
        }
    }

    @Override
    public void delete(long id) {
        try {
            dao.deleteById(id);
        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new BadRequestException("Запись с индентификатором %d не найдна".formatted(id), dataAccessException);
        }
    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {
        dao.deleteByIdIn(ids);
    }

    @Override
    public int delete(FilterParameters filters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        Subquery<Long> subQuery = getSubQuery(filters);

        CriteriaDelete<E> criteriaDelete = criteriaBuilder
            .createCriteriaDelete(classEntity);

        Root<E> deleteRoot = criteriaDelete.from(classEntity);

        criteriaDelete
            .where(criteriaBuilder.in(deleteRoot.get("id")).value(subQuery));

        return entityManager.createQuery(criteriaDelete).executeUpdate();
    }

    private Subquery<Long> getSubQuery(FilterParameters filters) {
        Specification<E> specification = SpecificationHelper.toSpecification(filters);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<E> criteriaQuery = criteriaBuilder
            .createQuery(classEntity);

        Subquery<Long> subQuery = criteriaQuery.subquery(Long.class);
        Root<E> subQueryRoot = subQuery.from(classEntity);

        subQuery
            .select(subQueryRoot.get("id"))
            .where(specification.toPredicate(subQueryRoot, criteriaBuilder.createQuery(), criteriaBuilder));

        return subQuery;
    }

}
