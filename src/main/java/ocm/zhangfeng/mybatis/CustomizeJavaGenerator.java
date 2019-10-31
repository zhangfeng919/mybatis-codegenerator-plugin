package ocm.zhangfeng.mybatis;

import java.util.ArrayList;
import java.util.List;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.SelectAllMethodGenerator;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.internal.util.messages.Messages;

/**
 * @author zhangfeng
 * @create 2019-10-30-17:12
 **/
public class CustomizeJavaGenerator extends JavaMapperGenerator {

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        this.progressCallback.startTask(
            Messages.getString("Progress.17",
                this.introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = this.context.getCommentGenerator();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
            this.introspectedTable.getMyBatis3JavaMapperType());
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);
        String rootInterface = this.introspectedTable
            .getTableConfigurationProperty("rootInterface");
        if (!StringUtility.stringHasValue(rootInterface)) {
            rootInterface = this.context.getJavaClientGeneratorConfiguration()
                .getProperty("rootInterface");
        }

        if (StringUtility.stringHasValue(rootInterface)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);
            interfaze.addSuperInterface(fqjt);
            interfaze.addImportedType(fqjt);
        }

        this.addCountByExampleMethod(interfaze);
        this.addDeleteByExampleMethod(interfaze);
        this.addDeleteByPrimaryKeyMethod(interfaze);
        this.addInsertMethod(interfaze);
        this.addInsertSelectiveMethod(interfaze);
        this.addSelectByExampleWithBLOBsMethod(interfaze);
        this.addSelectByExampleWithoutBLOBsMethod(interfaze);
        this.addSelectByPrimaryKeyMethod(interfaze);
        this.addUpdateByExampleSelectiveMethod(interfaze);
        this.addUpdateByExampleWithBLOBsMethod(interfaze);
        this.addUpdateByExampleWithoutBLOBsMethod(interfaze);
        this.addUpdateByPrimaryKeySelectiveMethod(interfaze);
        this.addUpdateByPrimaryKeyWithBLOBsMethod(interfaze);
        this.addUpdateByPrimaryKeyWithoutBLOBsMethod(interfaze);

        addSelectAllMethod(interfaze);

        List<CompilationUnit> answer = new ArrayList();
        if (this.context.getPlugins()
            .clientGenerated(interfaze, (TopLevelClass) null, this.introspectedTable)) {
            answer.add(interfaze);
        }

        List<CompilationUnit> extraCompilationUnits = this.getExtraCompilationUnits();
        if (extraCompilationUnits != null) {
            answer.addAll(extraCompilationUnits);
        }

        return answer;
    }


    protected void addSelectAllMethod(Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new CustomizeSelectAllMethodGenerator();
        initializeAndExecuteGenerator(methodGenerator, interfaze);
    }
}
