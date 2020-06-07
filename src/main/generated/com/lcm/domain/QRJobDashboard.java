package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRJobDashboard is a Querydsl query type for RJobDashboard
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRJobDashboard extends EntityPathBase<RJobDashboard> {

    private static final long serialVersionUID = -1634493948L;

    public static final QRJobDashboard rJobDashboard = new QRJobDashboard("rJobDashboard");

    public final StringPath area = createString("area");

    public final StringPath assignShift = createString("assignShift");

    public final DatePath<java.time.LocalDate> assignShiftDate = createDate("assignShiftDate", java.time.LocalDate.class);

    public final NumberPath<Float> changeDuration = createNumber("changeDuration", Float.class);

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

    public QRJobDashboard(String variable) {
        super(RJobDashboard.class, forVariable(variable));
    }

    public QRJobDashboard(Path<? extends RJobDashboard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRJobDashboard(PathMetadata metadata) {
        super(RJobDashboard.class, metadata);
    }

}

