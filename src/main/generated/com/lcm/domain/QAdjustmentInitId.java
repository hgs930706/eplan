package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdjustmentInitId is a Querydsl query type for AdjustmentInitId
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QAdjustmentInitId extends BeanPath<AdjustmentInitId> {

    private static final long serialVersionUID = -394078435L;

    public static final QAdjustmentInitId adjustmentInitId = new QAdjustmentInitId("adjustmentInitId");

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

    public QAdjustmentInitId(String variable) {
        super(AdjustmentInitId.class, forVariable(variable));
    }

    public QAdjustmentInitId(Path<? extends AdjustmentInitId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdjustmentInitId(PathMetadata metadata) {
        super(AdjustmentInitId.class, metadata);
    }

}

