package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWoxx is a Querydsl query type for Woxx
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWoxx extends EntityPathBase<Woxx> {

    private static final long serialVersionUID = -651067299L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWoxx woxx = new QWoxx("woxx");

    public final StringPath defaultFinalGrade = createString("defaultFinalGrade");

    public final StringPath isClosed = createString("isClosed");

    public final DateTimePath<java.time.LocalDateTime> lmTime = createDateTime("lmTime", java.time.LocalDateTime.class);

    public final StringPath lmUser = createString("lmUser");

    public final StringPath materialCategory = createString("materialCategory");

    public final StringPath partNo = createString("partNo");

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public final StringPath status = createString("status");

    public final StringPath validCellGrade = createString("validCellGrade");

    public final StringPath woIqty = createString("woIqty");

    public final StringPath woQtyTotal = createString("woQtyTotal");

    public final StringPath woType = createString("woType");

    public final QWoxxId woxxId;

    public QWoxx(String variable) {
        this(Woxx.class, forVariable(variable), INITS);
    }

    public QWoxx(Path<? extends Woxx> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWoxx(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWoxx(PathMetadata metadata, PathInits inits) {
        this(Woxx.class, metadata, inits);
    }

    public QWoxx(Class<? extends Woxx> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.woxxId = inits.isInitialized("woxxId") ? new QWoxxId(forProperty("woxxId")) : null;
    }

}

