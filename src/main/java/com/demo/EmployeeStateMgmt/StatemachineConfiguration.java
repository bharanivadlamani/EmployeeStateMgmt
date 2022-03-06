package com.demo.EmployeeStateMgmt;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;


@Configuration
@EnableStateMachine
class StatemachineConfiguration extends StateMachineConfigurerAdapter<EmployeeStates, EmployeeEvents> {

	@Override
	public void configure(StateMachineTransitionConfigurer<EmployeeStates, EmployeeEvents> transitions) throws Exception {
		transitions
				.withExternal().source(EmployeeStates.ADDED).target(EmployeeStates.IN_CHECK).event(EmployeeEvents.BEGIN_CHECK)
				.and()
				.withExternal().source(EmployeeStates.IN_CHECK).target(EmployeeStates.APPROVED).event(EmployeeEvents.APPROVE)
				.and()
				.withExternal().source(EmployeeStates.APPROVED).target(EmployeeStates.IN_CHECK).event(EmployeeEvents.UNAPPROVE)
				.and()
				.withExternal().source(EmployeeStates.APPROVED).target(EmployeeStates.ACTIVE).event(EmployeeEvents.ACTIVATE);
	}

	@Override
	public void configure(StateMachineStateConfigurer<EmployeeStates, EmployeeEvents> states) throws Exception {
		states
				.withStates()
				.initial(EmployeeStates.ADDED)
				.states(EnumSet.allOf(EmployeeStates.class));
	}

	@Override
	public void configure(StateMachineConfigurationConfigurer<EmployeeStates, EmployeeEvents> config) throws Exception {

		StateMachineListenerAdapter<EmployeeStates, EmployeeEvents> adapter = new StateMachineListenerAdapter<EmployeeStates, EmployeeEvents>() {
			@Override
			public void stateChanged(State<EmployeeStates, EmployeeEvents> from, State<EmployeeStates, EmployeeEvents> to) {
				System.out.println(String.format("stateChanged(from: %s, to: %s)", from + "", to + ""));
			}
		};
		config.withConfiguration()
				.autoStartup(false)
				.listener(adapter);
	}
}
