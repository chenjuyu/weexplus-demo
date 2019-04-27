/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
// Copyright 2014 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

// This file is autogenerated by
//     incubator-weex/weex_core/Source/android/jniprebuild/jni_generator.py
// For
//     com/taobao/weex/base/SystemMessageHandler

#ifndef com_taobao_weex_base_SystemMessageHandler_JNI
#define com_taobao_weex_base_SystemMessageHandler_JNI

#include <jni.h>

#include "base/android/jni/android_jni.h"

// Step 1: forward declarations.
namespace {
const char kSystemMessageHandlerClassPath[] =
    "com/taobao/weex/base/SystemMessageHandler";
// Leaking this jclass as we cannot use LazyInstance from some threads.
jclass g_SystemMessageHandler_clazz = NULL;
#define SystemMessageHandler_clazz(env) g_SystemMessageHandler_clazz

}  // namespace

static void RunWork(JNIEnv* env, jobject jcaller,
    jlong delegateNative);

// Step 2: method stubs.

static intptr_t g_SystemMessageHandler_create = 0;
static base::android::ScopedLocalJavaRef<jobject>
    Java_SystemMessageHandler_create(JNIEnv* env, jlong
    messagePumpDelegateNative) {
  /* Must call RegisterNativesImpl()  */
  //CHECK_CLAZZ(env, SystemMessageHandler_clazz(env),
  //    SystemMessageHandler_clazz(env), NULL);
  jmethodID method_id =
      base::android::GetMethod(
      env, SystemMessageHandler_clazz(env),
      base::android::STATIC_METHOD,
      "create",

"("
"J"
")"
"Lcom/taobao/weex/base/SystemMessageHandler;",
      &g_SystemMessageHandler_create);

  jobject ret =
      env->CallStaticObjectMethod(SystemMessageHandler_clazz(env),
          method_id, messagePumpDelegateNative);
  base::android::CheckException(env);
  return base::android::ScopedLocalJavaRef<jobject>(env, ret);
}

static intptr_t g_SystemMessageHandler_scheduleWork = 0;
static void Java_SystemMessageHandler_scheduleWork(JNIEnv* env, jobject obj) {
  /* Must call RegisterNativesImpl()  */
  //CHECK_CLAZZ(env, obj,
  //    SystemMessageHandler_clazz(env));
  jmethodID method_id =
      base::android::GetMethod(
      env, SystemMessageHandler_clazz(env),
      base::android::INSTANCE_METHOD,
      "scheduleWork",

"("
")"
"V",
      &g_SystemMessageHandler_scheduleWork);

     env->CallVoidMethod(obj,
          method_id);
  base::android::CheckException(env);

}

static intptr_t g_SystemMessageHandler_scheduleDelayedWork = 0;
static void Java_SystemMessageHandler_scheduleDelayedWork(JNIEnv* env, jobject
    obj, jlong delayMillis) {
  /* Must call RegisterNativesImpl()  */
  //CHECK_CLAZZ(env, obj,
  //    SystemMessageHandler_clazz(env));
  jmethodID method_id =
      base::android::GetMethod(
      env, SystemMessageHandler_clazz(env),
      base::android::INSTANCE_METHOD,
      "scheduleDelayedWork",

"("
"J"
")"
"V",
      &g_SystemMessageHandler_scheduleDelayedWork);

     env->CallVoidMethod(obj,
          method_id, delayMillis);
  base::android::CheckException(env);

}

static intptr_t g_SystemMessageHandler_stop = 0;
static void Java_SystemMessageHandler_stop(JNIEnv* env, jobject obj) {
  /* Must call RegisterNativesImpl()  */
  //CHECK_CLAZZ(env, obj,
  //    SystemMessageHandler_clazz(env));
  jmethodID method_id =
      base::android::GetMethod(
      env, SystemMessageHandler_clazz(env),
      base::android::INSTANCE_METHOD,
      "stop",

"("
")"
"V",
      &g_SystemMessageHandler_stop);

     env->CallVoidMethod(obj,
          method_id);
  base::android::CheckException(env);

}

// Step 3: RegisterNatives.

static const JNINativeMethod kMethodsSystemMessageHandler[] = {
    { "nativeRunWork",
"("
"J"
")"
"V", reinterpret_cast<void*>(RunWork) },
};

static bool RegisterNativesImpl(JNIEnv* env) {

  g_SystemMessageHandler_clazz = reinterpret_cast<jclass>(env->NewGlobalRef(
      base::android::GetClass(env, kSystemMessageHandlerClassPath).Get()));

  const int kMethodsSystemMessageHandlerSize =
      sizeof(kMethodsSystemMessageHandler)/sizeof(kMethodsSystemMessageHandler[0]);

  if (env->RegisterNatives(SystemMessageHandler_clazz(env),
                           kMethodsSystemMessageHandler,
                           kMethodsSystemMessageHandlerSize) < 0) {
    //jni_generator::HandleRegistrationError(
    //    env, SystemMessageHandler_clazz(env), __FILE__);
    return false;
  }

  return true;
}

#endif  // com_taobao_weex_base_SystemMessageHandler_JNI
