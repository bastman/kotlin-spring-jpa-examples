package com.example;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QFooEntity is a Querydsl query type for FooEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFooEntity extends EntityPathBase<FooEntity> {

    private static final long serialVersionUID = 904699256L;

    public static final QFooEntity fooEntity = new QFooEntity("fooEntity");

    public final ComparablePath<java.util.UUID> uuid = createComparable("uuid", java.util.UUID.class);

    public QFooEntity(String variable) {
        super(FooEntity.class, forVariable(variable));
    }

    public QFooEntity(Path<? extends FooEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFooEntity(PathMetadata metadata) {
        super(FooEntity.class, metadata);
    }

}

