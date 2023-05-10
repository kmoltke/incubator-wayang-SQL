/*
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
package org.apache.wayang.spark.monitoring.metrics;
import org.apache.wayang.spark.monitoring.interfaces.Application;
import org.apache.wayang.spark.monitoring.interfaces.Job;
import org.apache.wayang.spark.monitoring.interfaces.SerializableObject;

import java.util.List;
/**
 * The {@code ApplicationEnd} class represents an application that has ended. It implements
 * the {@link Application} interface and the {@link SerializableObject} interface, indicating
 * that it is an application that can be serialized and deserialized to and from a stream of bytes.
 * <p>
 * This class contains fields to store the name, time, ID, Spark user, event name, and list of jobs
 * associated with the application. It also provides methods to set and get the values of these fields.
 * </p>
 *
 * @see Application
 * @see SerializableObject
 */
public class ApplicationEnd implements Application, SerializableObject {
    private String name;
    private long time;
    private String id;
    private String sparkUser;
    private String eventName;
    private List<Job> listOfJobs;
    @Override
    public void setEventame(String name) {
        this.eventName=name;
    }

    @Override
    public String getEventName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setStartTime(long time) {
        this.time=time;
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public void setAppID(String id) {
        this.id=id;
    }

    @Override
    public String getAppID() {
        return id;
    }

    @Override
    public void setSparkUser(String user) {
        this.sparkUser=user;
    }

    @Override
    public String getSparkUser() {
        return sparkUser;
    }

    @Override
    public void setListOfJobs(List<Job> listOfJobs) {
        this.listOfJobs=listOfJobs;
    }

    @Override
    public List<Job> getListOfjobs() {
        return listOfJobs;
    }
}

