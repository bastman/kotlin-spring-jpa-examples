package com.example.demo.querydsl

import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.BooleanExpression

fun BooleanExpression.andAllOf(predicates: List<Predicate>): BooleanExpression {
    val t=predicates.toTypedArray()
    return this.and(ExpressionUtils.allOf(*t))
}
fun BooleanExpression.orAllOf(predicates: List<Predicate>): BooleanExpression {
    val t=predicates.toTypedArray()
    return this.or(ExpressionUtils.allOf(*t))
}
fun BooleanExpression.andAnyOf(predicates: List<Predicate>): BooleanExpression {
    val t=predicates.toTypedArray()
    return this.and(ExpressionUtils.anyOf(*t))
}
fun BooleanExpression.orAnyOf(predicates: List<Predicate>): BooleanExpression {
    val t=predicates.toTypedArray()
    return this.or(ExpressionUtils.anyOf(*t))
}