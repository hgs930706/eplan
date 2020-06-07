package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdjustmentInit is a Querydsl query type for AdjustmentInit
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAdjustmentInit extends EntityPathBase<AdjustmentInit> {

    private static final long serialVersionUID = 893443682L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdjustmentInit adjustmentInit = new QAdjustmentInit("adjustmentInit");

    public final QAdjustmentInitId adjustmentInitId;

    public final StringPath changeLevel = createString("changeLevel");

    public final StringPath duration = createString("duration");

    public final DateTimePath<java.time.LocalDateTime> lmTime = createDateTime("lmTime", java.time.LocalDateTime.class);

    public final StringPath lmUser = createString("lmUser");

    public final StringPath partNo = createString("partNo");

    public final NumberPath<Integer> planQty = createNumber("planQty", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> processEndTime = createDateTime("processEndTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> processStartTime = createDateTime("processStartTime", java.time.LocalDateTime.class);

    public final StringPath remark = createString("remark");

    public final StringPath runSeq = createString("runSeq");

    public QAdjustmentInit(String variable) {
        this(AdjustmentInit.class, forVariable(variable), INITS);
    }

    public QAdjustmentInit(Path<? extends AdjustmentInit> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdjustmentInit(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdjustmentInit(PathMetadata metadata, PathInits inits) {
        this(AdjustmentInit.class, metadata, inits);
    }

    public QAdjustmentInit(Class<? extends AdjustmentInit> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.adjustmentInitId = inits.isInitialized("adjustmentInitId") ? new QAdjustmentInitId(forProperty("adjustmentInitId")) : null;
    }

}

