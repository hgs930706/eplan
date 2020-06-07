package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSJobScore is a Querydsl query type for SJobScore
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSJobScore extends EntityPathBase<SJobScore> {

    private static final long serialVersionUID = -151939837L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSJobScore sJobScore = new QSJobScore("sJobScore");

    public final StringPath itemValue = createString("itemValue");

    public final DateTimePath<java.time.LocalDateTime> lmTime = createDateTime("lmTime", java.time.LocalDateTime.class);

    public final StringPath lmUser = createString("lmUser");

    public final QSJobScoreId sjobscoreId;

    public QSJobScore(String variable) {
        this(SJobScore.class, forVariable(variable), INITS);
    }

    public QSJobScore(Path<? extends SJobScore> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSJobScore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSJobScore(PathMetadata metadata, PathInits inits) {
        this(SJobScore.class, metadata, inits);
    }

    public QSJobScore(Class<? extends SJobScore> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sjobscoreId = inits.isInitialized("sjobscoreId") ? new QSJobScoreId(forProperty("sjobscoreId")) : null;
    }

}

