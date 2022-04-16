package com.example.jpa;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Optional;

public class OffsetPagination implements Pageable, Serializable {

    private int itemsPerPage;
    private int startIndex;
    private final Sort sort;

    public OffsetPagination(int startIndex, int itemsPerPage, Sort sort) {
        if (startIndex < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }

        if (itemsPerPage < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }
        this.itemsPerPage = itemsPerPage;
        this.startIndex = startIndex;
        this.sort = sort;
    }

    public OffsetPagination(int startIndex, int itemsPerPage, Sort.Direction direction, String... properties) {
        this(startIndex, itemsPerPage, Sort.by(direction, properties));
    }

    public OffsetPagination(int startIndex, int itemsPerPage) {
        this(startIndex, itemsPerPage, Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public boolean isPaged() {
        return Pageable.super.isPaged();
    }

    @Override
    public boolean isUnpaged() {
        return Pageable.super.isUnpaged();
    }

    @Override
    public int getPageNumber() {
        return startIndex / itemsPerPage;
    }

    @Override
    public int getPageSize() {
        return itemsPerPage;
    }

    @Override
    public long getOffset() {
        return startIndex;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Sort getSortOr(Sort sort) {
        return Pageable.super.getSortOr(sort);
    }

    @Override
    public Pageable next() {
        return new OffsetPagination(this.startIndex + this.itemsPerPage, getPageSize(), getSort());
    }

    public OffsetPagination previous() {
        return hasPrevious()
            ? new OffsetPagination(this.startIndex - this.itemsPerPage, this.itemsPerPage, getSort())
            : this;
    }


    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetPagination(0, this.itemsPerPage, getSort());
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return new OffsetPagination(pageNumber * this.itemsPerPage + 1, this.itemsPerPage * 4, getSort());
    }

    @Override
    public boolean hasPrevious() {
        return startIndex > itemsPerPage;
    }

    @Override
    public Optional<Pageable> toOptional() {
        return Pageable.super.toOptional();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof OffsetPagination)) return false;

        OffsetPagination that = (OffsetPagination) o;

        return new EqualsBuilder()
            .append(itemsPerPage, that.itemsPerPage)
            .append(startIndex, that.startIndex)
            .append(sort, that.sort)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(itemsPerPage)
            .append(startIndex)
            .append(sort)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("limit", itemsPerPage)
            .append("offset", startIndex)
            .append("sort", sort)
            .toString();
    }

}
