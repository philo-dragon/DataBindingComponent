package com.pfl.common.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by rocky on 2017/12/29.
 */

@Scope
@Documented
@Retention(value = RUNTIME)
public @interface FragmentScope {

}
