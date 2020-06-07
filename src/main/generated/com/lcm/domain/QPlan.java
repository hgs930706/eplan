package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPlan is a Querydsl query type for Plan
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPlan extends EntityPathBase<Plan> {

    private static final long serialVersionUID = -651279442L;

    public static final QPlan plan = new QPlan("plan");

    public final StringPath area = createString("area");

    public final StringPath cellPartNo = createString("cellPartNo");

    public final StringPath fab = createString("fab");

    public final StringPath grade = createString("grade");

    public final StringPath jobId = createString("jobId");

    public final StringPath jobType = createString("jobType");

    public final StringPath line = createString("line");

    public final DateTimePath<java.time.LocalDateTime> lmTime = createDateTime("lmTime", java.time.LocalDateTime.class);

    public final StringPath lmUser = createString("lmUser");

    public final StringPath modelNo = createString("modelNo");

    public final StringPath partNo = createString("partNo");

    public final StringPath planQty = createString("planQty");

    public final DatePath<java.time.LocalDate> shiftDate = createDate("shiftDate", java.time.LocalDate.class);

    public final StringPath site = createString("site");

    public QPlan(String variable) {
        super(Plan.class, forVariable(variable));
    }

    public QPlan(Path<? extends Plan> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlan(PathMetadata metadata) {
        super(Plan.class, metadata);
    }

}

