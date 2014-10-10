package eu.nyerel.panda.instrumentation.jdbc;

import eu.nyerel.panda.instrumentation.AbstractClassFileTransformer;
import eu.nyerel.panda.instrumentation.ClassTransformer;
import eu.nyerel.panda.instrumentation.jdbc.transformers.ConnectionCreatePreparedStatementTransformer;
import eu.nyerel.panda.instrumentation.jdbc.transformers.PreparedStatementTransformer;
import eu.nyerel.panda.instrumentation.jdbc.transformers.StatementTransformer;
import eu.nyerel.panda.util.CollectionUtil;
import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.util.*;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class JdbcClassFileTransformer extends AbstractClassFileTransformer implements ClassFileTransformer {

	private static final Set<String> STATEMENT_CLASSES = CollectionUtil.set(
			"org.postgresql.jdbc2.AbstractJdbc2Statement",
			"org.postgresql.jdbc3.AbstractJdbc3Statement",
			"org.postgresql.jdbc3g.AbstractJdbc3gStatement",
			"org.postgresql.jdbc4.AbstractJdbc4Statement",
			"org.postgresql.jdbc4.Jdbc4Statement");

	private static final Set<String> PREPARED_STATEMENT_CLASSES = CollectionUtil.set(
			"org.postgresql.jdbc2.AbstractJdbc2Statement",
			"org.postgresql.jdbc3.AbstractJdbc3Statement",
			"org.postgresql.jdbc3g.AbstractJdbc3gStatement",
			"org.postgresql.jdbc4.AbstractJdbc4Statement",
			"org.postgresql.jdbc4.Jdbc4Statement",
			"org.postgresql.jdbc4.Jdbc4PreparedStatement");

	private static final Set<String> CONNECTION_CLASSES = CollectionUtil.set(
			"org.postgresql.jdbc2.AbstractJdbc2Connection",
			"org.postgresql.jdbc3.AbstractJdbc3Connection",
			"org.postgresql.jdbc3g.AbstractJdbc3gConnection",
			"org.postgresql.jdbc4.AbstractJdbc4Connection",
			"org.postgresql.jdbc4.Jdbc4Connection");

	private static final Map<ClassTransformer, Set<String>> CLASS_TRANSFORMER_MAP = CollectionUtil.linkedMap(
			new AbstractMap.SimpleEntry<ClassTransformer, Set<String>>(new StatementTransformer(), STATEMENT_CLASSES),
			new AbstractMap.SimpleEntry<ClassTransformer, Set<String>>(new PreparedStatementTransformer(), PREPARED_STATEMENT_CLASSES),
			new AbstractMap.SimpleEntry<ClassTransformer, Set<String>>(new ConnectionCreatePreparedStatementTransformer(), CONNECTION_CLASSES)
	);

	@Override
	protected boolean shouldTransform(String className) {
		return STATEMENT_CLASSES.contains(className);
	}

	@Override
	protected void doTransform(CtClass ctClass) throws NotFoundException, CannotCompileException {
		for (ClassTransformer classTransformer : CLASS_TRANSFORMER_MAP.keySet()) {
			Set<String> classes = CLASS_TRANSFORMER_MAP.get(classTransformer);
			if (classes.contains(ctClass.getName())) {
				classTransformer.doTransform(ctClass);
			}
		}
	}

	@Override
	protected String getTransformedFlag() {
		return "jdbc_inspection";
	}

}
