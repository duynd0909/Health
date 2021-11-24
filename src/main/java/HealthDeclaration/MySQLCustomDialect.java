package HealthDeclaration;

import org.hibernate.dialect.MySQLInnoDBDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MySQLCustomDialect extends MySQLInnoDBDialect {
    public MySQLCustomDialect() {
        super();
        registerFunction("group_concat",
                new StandardSQLFunction("group_concat",
                        StandardBasicTypes.STRING));
    }
}
