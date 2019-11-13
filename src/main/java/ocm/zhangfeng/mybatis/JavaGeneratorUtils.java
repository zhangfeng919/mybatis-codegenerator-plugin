package ocm.zhangfeng.mybatis;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.JavaBeansUtil;

/**
 * @author zhangfeng
 * @create 2019-10-31-13:52
 **/
public class JavaGeneratorUtils {

    public final static String SERVICE = "Service";
    public final static String API = "Api";
    public final static String SERVICEIMPL = "ServiceImpl";
    public final static String QUERY = "Query";
    public final static String PAGEQUERY = "PageQuery";
    public final static String DTO = "DTO";
    public final static String APIRESULTDTO = "APIResultDTO";
    public final static String APILISTDTO = "ApiListDTO";

    public final static String PAGEDTOTYPE = "com.zhangfeng.utils.base.POJO.DTO.PageDTO";
    public final static String APILISTDTOTYPE = "com.zhangfeng.utils.base.POJO.DTO.ApiListDTO";
    public final static String APIRESULTDTOTYPE = "com.zhangfeng.utils.base.POJO.DTO.APIResultDTO";


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
        String pojoPack = recordPack.substring(0, recordPack.lastIndexOf("."));
        return pojoPack.substring(0, pojoPack.lastIndexOf(".")) + ".service";
    }

    public static String buildApiPackagePath(IntrospectedTable introspectedTable) {
        String recordType = introspectedTable.getBaseRecordType();
        String recordPack = recordType.substring(0, recordType.lastIndexOf("."));
        String pojoPack = recordPack.substring(0, recordPack.lastIndexOf("."));
        return pojoPack.substring(0, pojoPack.lastIndexOf(".")) + ".apis";
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

    public static FullyQualifiedJavaType getApiJavaType(Context context,
        IntrospectedTable introspectedTable) {
        String objectName = getObjectName(introspectedTable);
        String pack = getApiPack(context, introspectedTable);
        return new FullyQualifiedJavaType(
            new StringBuffer(pack).append(".").
                append(objectName).
                append(API).toString());

    }

    public static FullyQualifiedJavaType getServiceJavaType(Context context,
        IntrospectedTable introspectedTable) {
        String objectName = getObjectName(introspectedTable);
        String pack = getServicePack(context, introspectedTable);
        return new FullyQualifiedJavaType(
            new StringBuffer(pack).append(".").
                append(objectName).
                append(SERVICE).toString());

    }
    public static FullyQualifiedJavaType getServiceImplJavaType(Context context,
        IntrospectedTable introspectedTable) {
        String objectName = getObjectName(introspectedTable);
        String pack = getServicePack(context, introspectedTable);
        return new FullyQualifiedJavaType(
            new StringBuffer(pack).append(".").
                append("impl.").
                append(objectName).
                append(SERVICEIMPL).toString());

    }

    public static FullyQualifiedJavaType getDTOJavaType(Context context,
        IntrospectedTable introspectedTable) {
        String objectName = getObjectName(introspectedTable);
        String pack = getDTOPack(context, introspectedTable);
        return new FullyQualifiedJavaType(
            new StringBuffer(pack).append(".").
                append(objectName).
                append(DTO).toString());

    }

    public static FullyQualifiedJavaType getPageQueryJavaType(Context context,
        IntrospectedTable introspectedTable) {
        String objectName = getObjectName(introspectedTable);
        String queryPack = getQueryPack(context, introspectedTable);
        return new FullyQualifiedJavaType(
            new StringBuffer(queryPack).append(".").
                append(objectName).
                append(PAGEQUERY).toString());

    }

    public static FullyQualifiedJavaType getQueryJavaType(Context context,
        IntrospectedTable introspectedTable) {
        String objectName = getObjectName(introspectedTable);
        String queryPack = getQueryPack(context, introspectedTable);
        return new FullyQualifiedJavaType(
            new StringBuffer(queryPack).append(".").
                append(objectName).
                append(QUERY).toString());

    }

    public static String getApiPack(Context context, IntrospectedTable introspectedTable) {
        String pack = context.getProperty("apiPackage");
        if (pack == null) {
            pack = buildApiPackagePath(introspectedTable);
        }

        return pack;
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

    public static String firstCharLower(String str){
        if(str == null){
            return  null;
        }
        if(str.length() == 1){
            return  str.toUpperCase();
        }
        return new StringBuilder().append(str.substring(0,1).toLowerCase()).
            append(str.substring(1)).toString();
    }


    public static Method createGetterMethod(Field field){
        Method method = new Method();
        method.setReturnType(field.getType());
        method.setName(JavaBeansUtil.getGetterMethodName(field.getName(),field.getType()));
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addBodyLine(new StringBuilder().
            append(" return ").append(field.getName()).append(";").toString());

        return method;
    }
    public static Method createSetterMethod(Field field){
        Method method = new Method();
        method.setName(JavaBeansUtil.getSetterMethodName(field.getName()));
        method.setVisibility(JavaVisibility.PUBLIC);

        Parameter parameter = new Parameter(field.getType(),field.getName());
        method.addParameter(parameter);

        method.addBodyLine(new StringBuilder().
            append(" this.").append(field.getName()).append(" = ").
            append(field.getName()).append(";").toString());

        return method;
    }

    public static String getObjectName(IntrospectedTable introspectedTable){
        return introspectedTable.getFullyQualifiedTable().getDomainObjectName();
    }
}
