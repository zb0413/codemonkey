package com.codemonkey.drools;

import org.apache.log4j.Logger;
import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import com.codemonkey.utils.SysUtils;

@SuppressWarnings("deprecation")
public class RuleRunner {

	static Logger logger = SysUtils.getLog(RuleRunner.class);

	public static void runRules(String[] rules, Object[] facts){

		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		
		if (rules == null) {
			return;
		}

		for (int i = 0; i < rules.length; i++) {

			String ruleFile = rules[i];

			logger.info("Loading file: " + ruleFile);
			
			kbuilder.add( ResourceFactory.newClassPathResource(ruleFile , RuleRunner.class), ResourceType.DRL );
			
		}
		
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error: errors) {
				logger.error(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
	    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

		if (facts != null) {
			for (int i = 0; i < facts.length; i++) {

				Object fact = facts[i];

				logger.info("Inserting fact: " + fact);

				ksession.insert(fact);
			}
		}

		ksession.fireAllRules();
	}

	public static void runRules(String rule, Object... facts) {
		RuleRunner.runRules(new String[]{rule}, facts);
	}
}
