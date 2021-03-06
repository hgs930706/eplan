package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QHJobDashboard is a Querydsl query type for HJobDashboard
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QHJobDashboard extends EntityPathBase<HJobDashboard> {

    private static final long serialVersionUID = 1299536122L;

    public static final QHJobDashboard hJobDashboard = new QHJobDashboard("hJobDashboard");

    public final StringPath area = createString("area");

    public final StringPath assignShift = createString("assignShift");

    public final DatePath<java.time.LocalDate> assignShiftDate = createDate("assignShiftDate", java.time.LocalDate.class);

    public final NumberPath<Integer> changeDuration = createNumber("changeDuration", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> changeEndTime = createDateTime("changeEndTime", java.time.LocalDateTime.class);

    public final StringPath changeKey = createString("changeKey");

    public final StringPath changeLevel = createString("changeLevel");

    public final StringPath changeShift = createString("changeShift");

    public final DatePath<java.time.LocalDate> changeShiftDate = createDate("changeShiftDate", java.time.LocalDate.class);

    public final DateTimePath<java.time.LocalDateTime> changeStartTime = createDateTime("changeStartTime", java.time.LocalDateTime.class);

    public final StringPath fab = createString("fab");

    public final NumberPath<Integer> forecastQty = createNumber("forecastQty", Integer.class);

    public final StringPath grade = createString("grade");

    public final StringPath jobId = createString("jobId");

    public final StringPath jobStatus = createString("jobStatus");

    public final StringPath jobType = createString("jobType");

    public final StringPath line = createString("line");

    public final DateTimePath<java.time.LocalDateTime> lmTime = createDateTime("lmTime", java.time.LocalDateTime.class);

    public final StringPath lmUser = createString("lmUser");

    public final StringPath modelNo = createString("modelNo");

    public final StringPath partNo = createString("partNo");

    public final NumberPath<Integer> planQty = createNumber("planQty", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> processEndTime = createDateTime("processEndTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> processStartTime = createDateTime("processStartTime", java.time.LocalDateTime.class);

    public final StringPath remark = createString("remark");

    public final StringPath shift = createString("shift");

    public final DatePath<java.time.LocalDate> shiftDate = createDate("shiftDate", java.time.LocalDate.class);

    public final StringPath site = createString("site");

    public final StringPath woId = createString("woId");

    public QHJobDashboard(String variable) {
        super(HJobDashboard.class, forVariable(variable));
    }

    public QHJobDashboard(Path<? extends HJobDashboard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHJobDashboard(PathMetadata metadata) {
        super(HJobDashboard.class, metadata);
    }

}

