/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Copyright (C) 2013-2016 Peng Li<aqnote@qq.com>.
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */ org.apache.hadoop.hdfs.protocol;

import org.apache.hadoop.util.StringUtils;

public class DSQuotaExceededException extends QuotaExceededException {
  protected static final long serialVersionUID = 1L;

  public DSQuotaExceededException(String msg) {
    super(msg);
  }

  public DSQuotaExceededException(long quota, long count) {
    super(quota, count);
  }

  public String getMessage() {
    String msg = super.getMessage();
    if (msg == null) {
      return "The DiskSpace quota" + (pathName==null?"":(" of " + pathName)) + 
          " is exceeded: quota=" + quota + " diskspace consumed=" + StringUtils.humanReadableInt(count);
    } else {
      return msg;
    }
  }
}
