package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdjustment is a Querydsl query type for Adjustment
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAdjustment extends EntityPathBase<Adjustment> {

    private static final long serialVersionUID = -431722958L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdjustment adjustment = new QAdjustment("adjustment");

    public final QAdjustmentId adjustmentId;

    public final StringPath changeLevel = createString("changeLevel");

    public final StringPath duration = createString("duration");

    public final DateTimePath<java.time.LocalDateTime> lmTime = createDateTime("lmTime", java.time.LocalDateTime.class);

    public final StringPath lmUser = createString("lmUser");

    public final StringPath partNo = createString("partNo");

    public final StringPath planQty = createString("planQty");

    public final DateTimePath<java.time.LocalDateTime> processEndTime = createDateTime("processEndTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> processStartTime = createDateTime("processStartTime", java.time.LocalDateTime.class);

    public final StringPath remark = createString("remark");

    public final StringPath runSeq = createString("runSeq");

    public QAdjustment(String variable) {
        this(Adjustment.class, forVariable(variable), INITS);
    }

    public QAdjustment(Path<? extends Adjustment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdjustment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdjustment(PathMetadata metadata, PathInits inits) {
        this(Adjustment.class, metadata, inits);
    }

    public QAdjustment(Class<? extends Adjustment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.adjustmentId = inits.isInitialized("adjustmentId") ? new QAdjustmentId(forProperty("adjustmentId")) : null;
    }

}

