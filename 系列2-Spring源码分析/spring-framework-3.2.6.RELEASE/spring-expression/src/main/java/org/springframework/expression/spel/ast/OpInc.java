/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.expression.spel.ast;

import org.springframework.expression.EvaluationException;
import org.springframework.expression.Operation;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.ExpressionState;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelMessage;
import org.springframework.util.Assert;

/**
 * Increment operator. Can be used in a prefix or postfix form. This will throw
 * appropriate exceptions if the operand in question does not support increment.
 *
 * @author Andy Clement
 * @since 3.2
 */
public class OpInc extends Operator {

	private boolean postfix; // false means prefix

	public OpInc(int pos, boolean postfix, SpelNodeImpl... operands) {
		super("++", pos, operands);
		Assert.notEmpty(operands);
		this.postfix = postfix;
	}

	@Override
	public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
		SpelNodeImpl operand = getLeftOperand();

		ValueRef lvalue = operand.getValueRef(state);

		final TypedValue operandTypedValue = lvalue.getValue();
		final Object operandValue = operandTypedValue.getValue();
		TypedValue returnValue = operandTypedValue;
		TypedValue newValue = null;

		if (operandValue instanceof Number) {
			Number op1 = (Number) operandValue;
			if (op1 instanceof Double) {
				newValue = new TypedValue(op1.doubleValue() + 1.0d, operandTypedValue.getTypeDescriptor());
			} else if (op1 instanceof Float) {
				newValue = new TypedValue(op1.floatValue() + 1.0f, operandTypedValue.getTypeDescriptor());
			} else if (op1 instanceof Long) {
				newValue = new TypedValue(op1.longValue() + 1L, operandTypedValue.getTypeDescriptor());
			} else if (op1 instanceof Short) {
				newValue = new TypedValue(op1.shortValue() + (short)1, operandTypedValue.getTypeDescriptor());
			} else {
				newValue = new TypedValue(op1.intValue() + 1, operandTypedValue.getTypeDescriptor());
			}
		}
		if (newValue==null) {
			try {
				newValue = state.operate(Operation.ADD, returnValue.getValue(), 1);
			} catch (SpelEvaluationException see) {
				if (see.getMessageCode()==SpelMessage.OPERATOR_NOT_SUPPORTED_BETWEEN_TYPES) {
					// This means the operand is not incrementable
					throw new SpelEvaluationException(operand.getStartPosition(),SpelMessage.OPERAND_NOT_INCREMENTABLE,operand.toStringAST());
				} else {
					throw see;
				}
			}
		}

		// set the name value
		try {
			lvalue.setValue(newValue.getValue());
		} catch (SpelEvaluationException see) {
			// if unable to set the value the operand is not writable (e.g. 1++ )
			if (see.getMessageCode()==SpelMessage.SETVALUE_NOT_SUPPORTED) {
				throw new SpelEvaluationException(operand.getStartPosition(),SpelMessage.OPERAND_NOT_INCREMENTABLE);
			} else {
				throw see;
			}
		}

		if (!postfix) {
			// the return value is the new value, not the original value
			returnValue = newValue;
		}

		return returnValue;
	}

	@Override
	public String toStringAST() {
		return new StringBuilder().append(getLeftOperand().toStringAST()).append("++").toString();
	}

	@Override
	public SpelNodeImpl getRightOperand() {
		return null;
	}

}
