package ocm.zhangfeng.mybatis;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.config.Context;

/**
 * @author zhangfeng
 * @create 2019-10-31-13:52
 **/
public class JavaGeneratorUtils {

    public final static String SERVICE = "Service";
    public final static String QUERY = "Query";
    public final static String PAGEQUERY = "PageQuery";
    public final static String DTO = "DTO";
    public final static String APIRESULTDTO = "APIResultDTO";
    public final static String APILISTDTO = "ApiListDTO";

    public static String buildPackagePath(IntrospectedTable introspectedTable) {
        String recordType = introspectedTable.getBaseRecordType();
        String recordPack = recordType.substring(0, recordType.lastIndexOf("."));
        return recordPack.substring(0, recordPack.lastIndexOf(".")) + ".query";
    }

    public static String buildDTOPackagePath(IntrospectedTable introspectedTable) {
        String recordType = introspectedTable.getBaseRecordType();
        String recordPack = recordType.substring(0, recordType.lastIndexOf("."));
        return recordPack.substring(0, recordPack.lastIndexOf(".")) + ".DTO";
    }

    public static String buildServicePackagePath(IntrospectedTable introspectedTable) {
        String recordType = introspectedTable.getBaseRecordType();
        String recordPack = recordType.substring(0, recordType.lastIndexOf("."));
        String pojoPack = recordType.substring(0, recordType.lastIndexOf("."));
        return recordPack.substring(0, pojoPack.lastIndexOf(".")) + ".service";
    }

    public static String getDTOPack(Context context, IntrospectedTable introspectedTable) {
        String dtoPack = context.getProperty("DTOPackage");
        if (dtoPack == null) {
            dtoPack = buildDTOPackagePath(introspectedTable);
        }

        return dtoPack;
    }

    public static String getQueryPack(Context context, IntrospectedTable introspectedTable) {
        String queryPack = context.getProperty("queryPackage");
        if (queryPack == null) {
            queryPack = buildPackagePath(introspectedTable);
        }

        return queryPack;
    }

    public static FullyQualifiedJavaType getServiceJavaType(Context context,
        IntrospectedTable introspectedTable,
        String objectName) {
        String pack = getServicePack(context, introspectedTable);
        return new FullyQualifiedJavaType(
            new StringBuffer(pack).append(".").
                append(objectName).
                append(SERVICE).toString());

    }

    public static FullyQualifiedJavaType getDTOJavaType(Context context,
        IntrospectedTable introspectedTable,
        String objectName) {
        String pack = getDTOPack(context, introspectedTable);
        return new FullyQualifiedJavaType(
            new StringBuffer(pack).append(".").
                append(objectName).
                append(DTO).toString());

    }

    public static FullyQualifiedJavaType getPageQueryJavaType(Context context,
        IntrospectedTable introspectedTable,
        String objectName) {
        String queryPack = getQueryPack(context, introspectedTable);
        return new FullyQualifiedJavaType(
            new StringBuffer(queryPack).append(".").
                append(objectName).
                append(PAGEQUERY).toString());

    }

    public static FullyQualifiedJavaType getQueryJavaType(Context context,
        IntrospectedTable introspectedTable,
        String objectName) {
        String queryPack = getQueryPack(context, introspectedTable);
        return new FullyQualifiedJavaType(
            new StringBuffer(queryPack).append(".").
                append(objectName).
                append(QUERY).toString());

    }

    public static String getServicePack(Context context, IntrospectedTable introspectedTable) {
        String pack = context.getProperty("servicePackage");
        if (pack == null) {
            pack = buildServicePackagePath(introspectedTable);
        }

        return pack;
    }

    public static FullyQualifiedJavaType getApiResultJavaType(Context context,
        IntrospectedTable introspectedTable) {
        return getApiResultJavaType(context, introspectedTable, null);
    }

    public static FullyQualifiedJavaType getApiListJavaType(Context context,
        IntrospectedTable introspectedTable) {
        return getApiListJavaType(context, introspectedTable, null);
    }

    public static FullyQualifiedJavaType getApiListJavaType(Context context,
        IntrospectedTable introspectedTable,
        FullyQualifiedJavaType dtoType) {
        String dtoPack = getDTOPack(context, introspectedTable);
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
            new StringBuffer(dtoPack).append(".").
                append(APILISTDTO).toString());

        if (dtoType != null) {
            type.addTypeArgument(dtoType);
        }

        return type;
    }

    public static FullyQualifiedJavaType getApiResultJavaType(Context context,
        IntrospectedTable introspectedTable,
        FullyQualifiedJavaType dtoType) {
        String dtoPack = getDTOPack(context, introspectedTable);
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
            new StringBuffer(dtoPack).append(".").
                append(APIRESULTDTO).toString());

        if (dtoType != null) {
            type.addTypeArgument(dtoType);
        }

        return type;
    }

    /**
     * 方法注释生成
     *
     * @param method
     * @param explain
     */
    public static void methodAnnotation(Method method, String explain) {
        // 生成注释
        StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(explain);
        method.addJavaDocLine(sb.toString());
        if (method.getParameters().size() > 0) {

        }
        method.getParameters().forEach(parameter -> {
            sb.setLength(0);
            sb.append(" * @param ");
            sb.append(parameter.getName());
            method.addJavaDocLine(sb.toString());
        });
        method.addJavaDocLine(" */");
        // 生成注释结束
    }

    /**
     * 属性注释生成
     *
     * @param field
     * @param explain
     */
    public static void fieldAnnotation(Field field, String explain) {
        if (explain == null || explain.trim().length() == 0) {
            return;
        }
        // 生成注释
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(explain);
        field.addJavaDocLine(sb.toString());
        field.addJavaDocLine(" */");
        // 生成注释结束
    }
}
