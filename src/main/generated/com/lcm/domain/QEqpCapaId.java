package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QEqpCapaId is a Querydsl query type for EqpCapaId
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QEqpCapaId extends BeanPath<EqpCapaId> {

    private static final long serialVersionUID = -1353952951L;

    public static final QEqpCapaId eqpCapaId = new QEqpCapaId("eqpCapaId");

    public final StringPath line = createString("line");

    public final StringPath partNo = createString("partNo");

    public final StringPath site = createString("site");

    public QEqpCapaId(String variable) {
        super(EqpCapaId.class, forVariable(variable));
    }

    public QEqpCapaId(Path<? extends EqpCapaId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEqpCapaId(PathMetadata metadata) {
        super(EqpCapaId.class, metadata);
    }

}

