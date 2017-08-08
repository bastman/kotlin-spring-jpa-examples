package com.example.demo.querydsl

import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.DateTimePath
import com.querydsl.core.types.dsl.NumberPath
import java.time.Instant

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


fun DateTimePath<Instant>.eq(value:String):BooleanExpression= this.eq(Instant.parse(value))
fun DateTimePath<Instant>.gt(value:String):BooleanExpression= this.gt(Instant.parse(value))
fun DateTimePath<Instant>.lt(value:String):BooleanExpression = this.lt(Instant.parse(value))
fun DateTimePath<Instant>.goe(value:String):BooleanExpression= this.goe(Instant.parse(value))
fun DateTimePath<Instant>.loe(value:String):BooleanExpression = this.loe(Instant.parse(value))

fun NumberPath<Int>.eq(value: String):BooleanExpression = this.eq(value.toInt())
fun NumberPath<Int>.gt(value: String):BooleanExpression = this.gt(value.toInt())
fun NumberPath<Int>.lt(value: String):BooleanExpression= this.lt(value.toInt())
fun NumberPath<Int>.goe(value: String):BooleanExpression= this.goe(value.toInt())
fun NumberPath<Int>.loe(value: String):BooleanExpression= this.loe(value.toInt())