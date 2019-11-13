package ocm.zhangfeng.mybatis;

import static ocm.zhangfeng.mybatis.JavaGeneratorUtils.fieldAnnotation;
import static ocm.zhangfeng.mybatis.JavaGeneratorUtils.methodAnnotation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ocm.zhangfeng.mybatis.javagenerator.ApiJavaGenerator;
import ocm.zhangfeng.mybatis.javagenerator.DTOJavaGenerator;
import ocm.zhangfeng.mybatis.javagenerator.QueryJavaGenerator;
import ocm.zhangfeng.mybatis.javagenerator.ServiceJavaGenerator;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import utils.LogUtil;

/**
 * @author zhangfeng
 * @create 2019-10-30-13:54
 **/
public class CustomizePlugin extends PluginAdapter {

    public boolean validate(List<String> list) {
        LogUtil.info(list);
        return true;
    }


    @Override
    public boolean sqlMapSelectAllElementGenerated(XmlElement element,
        IntrospectedTable introspectedTable) {
        super.sqlMapSelectAllElementGenerated(element, introspectedTable);
        return true;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze,
        IntrospectedTable introspectedTable) {
        methodAnnotation(method, "根据主键查询");
        return super.clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze,
        IntrospectedTable introspectedTable) {
        methodAnnotation(method, "新增记录忽略默认值");
        return super.clientInsertMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze,
        IntrospectedTable introspectedTable) {
        methodAnnotation(method, "新增记录采用默认值");
        return super.clientInsertSelectiveMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method,
        Interface interfaze, IntrospectedTable introspectedTable) {
        methodAnnotation(method, "修改记录");
        return super
            .clientUpdateByPrimaryKeySelectiveMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method,
        Interface interfaze, IntrospectedTable introspectedTable) {
        methodAnnotation(method, "修改记录");
        return super
            .clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientBasicDeleteMethodGenerated(Method method, Interface interfaze,
        IntrospectedTable introspectedTable) {
        methodAnnotation(method, "删除记录");
        return super.clientBasicDeleteMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientBasicUpdateMethodGenerated(Method method, Interface interfaze,
        IntrospectedTable introspectedTable) {
        methodAnnotation(method, "修改记录");
        return super.clientBasicUpdateMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze,
        IntrospectedTable introspectedTable) {
        methodAnnotation(method, "根据主键删除记录");
        return super.clientDeleteByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientSelectAllMethodGenerated(Method method, Interface interfaze,
        IntrospectedTable introspectedTable) {
        methodAnnotation(method, "列表查询");
        return super.clientSelectAllMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method,
        Interface interfaze, IntrospectedTable introspectedTable) {
        methodAnnotation(method, "修改记录");
        return super.clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(method, interfaze,
            introspectedTable);
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass,
        IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable,
        ModelClassType modelClassType) {
        fieldAnnotation(field, introspectedColumn.getRemarks());
        return super
            .modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable,
                modelClassType);
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
        IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> results = new ArrayList<>();

        //results.addAll(getFiles(new QueryJavaGenerator(),introspectedTable));

        results.addAll(getFiles(new ServiceJavaGenerator(),introspectedTable));

        results.addAll(getFiles(new DTOJavaGenerator(),introspectedTable));

        results.addAll(getFiles(new ApiJavaGenerator(),introspectedTable));


        return results;
    }

    List<GeneratedJavaFile> getFiles(AbstractJavaGenerator repository,
        IntrospectedTable introspectedTable){
        repository.setContext(context);
        repository.setIntrospectedTable(introspectedTable);
        List units = repository.getCompilationUnits();
        List generatedFile = new ArrayList();
        GeneratedJavaFile gif;
        for (Iterator iterator = units.iterator(); iterator.hasNext(); generatedFile.add(gif)) {
            CompilationUnit unit = (CompilationUnit) iterator.next();
            gif = new GeneratedJavaFile(unit,
                context.getJavaModelGeneratorConfiguration().getTargetProject(),
                context.getProperty("javaFileEncoding"), context.getJavaFormatter());
        }
        return generatedFile;
    }


}
