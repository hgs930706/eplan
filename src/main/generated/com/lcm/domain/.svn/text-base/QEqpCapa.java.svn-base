package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEqpCapa is a Querydsl query type for EqpCapa
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEqpCapa extends EntityPathBase<EqpCapa> {

    private static final long serialVersionUID = 1379595150L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEqpCapa eqpCapa = new QEqpCapa("eqpCapa");

    public final StringPath area = createString("area");

    public final QEqpCapaId eqpCapaId;

    public final StringPath fab = createString("fab");

    public final NumberPath<Integer> fabPcCapa = createNumber("fabPcCapa", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> lmTime = createDateTime("lmTime", java.time.LocalDateTime.class);

    public final StringPath lmUser = createString("lmUser");

    public final NumberPath<Integer> manpowerKilo = createNumber("manpowerKilo", Integer.class);

    public final StringPath modelNo = createString("modelNo");

    public final NumberPath<Integer> ppcCapa = createNumber("ppcCapa", Integer.class);

    public QEqpCapa(String variable) {
        this(EqpCapa.class, forVariable(variable), INITS);
    }

    public QEqpCapa(Path<? extends EqpCapa> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEqpCapa(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEqpCapa(PathMetadata metadata, PathInits inits) {
        this(EqpCapa.class, metadata, inits);
    }

    public QEqpCapa(Class<? extends EqpCapa> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.eqpCapaId = inits.isInitialized("eqpCapaId") ? new QEqpCapaId(forProperty("eqpCapaId")) : null;
    }

}

