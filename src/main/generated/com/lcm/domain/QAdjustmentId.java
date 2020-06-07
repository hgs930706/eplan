package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdjustmentId is a Querydsl query type for AdjustmentId
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QAdjustmentId extends BeanPath<AdjustmentId> {

    private static final long serialVersionUID = 1726067437L;

    public static final QAdjustmentId adjustmentId = new QAdjustmentId("adjustmentId");

    public final StringPath area = createString("area");

    public final StringPath changeKey = createString("changeKey");

    public final StringPath fab = createString("fab");

    public final StringPath grade = createString("grade");

    public final StringPath jobType = createString("jobType");

    public final StringPath line = createString("line");

    public final StringPath modelNo = createString("modelNo");

    public final StringPath partLevel = createString("partLevel");

    public final StringPath shift = createString("shift");

    public final DatePath<java.time.LocalDate> shiftDate = createDate("shiftDate", java.time.LocalDate.class);

    public final StringPath site = createString("site");

    public final StringPath woId = createString("woId");

    public QAdjustmentId(String variable) {
        super(AdjustmentId.class, forVariable(variable));
    }

    public QAdjustmentId(Path<? extends AdjustmentId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdjustmentId(PathMetadata metadata) {
        super(AdjustmentId.class, metadata);
    }

}

