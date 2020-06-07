package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QParParameter is a Querydsl query type for ParParameter
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QParParameter extends EntityPathBase<ParParameter> {

    private static final long serialVersionUID = -2074825843L;

    public static final QParParameter parParameter = new QParParameter("parParameter");

    public final StringPath area = createString("area");

    public final StringPath fab = createString("fab");

    public final StringPath inValue1 = createString("inValue1");

    public final StringPath inValue2 = createString("inValue2");

    public final StringPath inValue3 = createString("inValue3");

    public final StringPath inValue4 = createString("inValue4");

    public final StringPath itemName = createString("itemName");

    public final DateTimePath<java.time.LocalDateTime> lmTime = createDateTime("lmTime", java.time.LocalDateTime.class);

    public final StringPath lmUser = createString("lmUser");

    public final StringPath remark = createString("remark");

    public final StringPath seq = createString("seq");

    public final StringPath site = createString("site");

    public QParParameter(String variable) {
        super(ParParameter.class, forVariable(variable));
    }

    public QParParameter(Path<? extends ParParameter> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParParameter(PathMetadata metadata) {
        super(ParParameter.class, metadata);
    }

}

