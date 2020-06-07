package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSJobDashboard is a Querydsl query type for SJobDashboard
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSJobDashboard extends EntityPathBase<SJobDashboard> {

    private static final long serialVersionUID = -1927896955L;

    public static final QSJobDashboard sJobDashboard = new QSJobDashboard("sJobDashboard");

    public final NumberPath<Double> affectCapaPercent = createNumber("affectCapaPercent", Double.class);

    public final NumberPath<Integer> affectCapaQty = createNumber("affectCapaQty", Integer.class);

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

    public final NumberPath<Integer> jobQty = createNumber("jobQty", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> jobReadyTime = createDateTime("jobReadyTime", java.time.LocalDateTime.class);

    public final StringPath jobType = createString("jobType");

    public final StringPath line = createString("line");

    public final DateTimePath<java.time.LocalDateTime> lmTime = createDateTime("lmTime", java.time.LocalDateTime.class);

    public final StringPath lmUser = createString("lmUser");

    public final StringPath modelNo = createString("modelNo");

    public final StringPath partNo = createString("partNo");

    public final DatePath<java.time.LocalDate> planEndDate = createDate("planEndDate", java.time.LocalDate.class);

    public final NumberPath<Integer> planQty = createNumber("planQty", Integer.class);

    public final DatePath<java.time.LocalDate> planStartDate = createDate("planStartDate", java.time.LocalDate.class);

    public final DateTimePath<java.time.LocalDateTime> processEndTime = createDateTime("processEndTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> processStartTime = createDateTime("processStartTime", java.time.LocalDateTime.class);

    public final StringPath shift = createString("shift");

    public final NumberPath<Integer> shiftCountAfterChange = createNumber("shiftCountAfterChange", Integer.class);

    public final DatePath<java.time.LocalDate> shiftDate = createDate("shiftDate", java.time.LocalDate.class);

    public final StringPath site = createString("site");

    public final StringPath trxId = createString("trxId");

    public final StringPath woId = createString("woId");

    public QSJobDashboard(String variable) {
        super(SJobDashboard.class, forVariable(variable));
    }

    public QSJobDashboard(Path<? extends SJobDashboard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSJobDashboard(PathMetadata metadata) {
        super(SJobDashboard.class, metadata);
    }

}

