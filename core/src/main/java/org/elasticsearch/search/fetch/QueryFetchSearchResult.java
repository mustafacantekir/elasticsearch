/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.search.fetch;

import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.search.SearchShardTarget;
import org.elasticsearch.search.query.QuerySearchResult;
import org.elasticsearch.search.query.QuerySearchResultProvider;

import java.io.IOException;

import static org.elasticsearch.search.fetch.FetchSearchResult.readFetchSearchResult;
import static org.elasticsearch.search.query.QuerySearchResult.readQuerySearchResult;

public class QueryFetchSearchResult extends QuerySearchResultProvider {

    private QuerySearchResult queryResult;
    private FetchSearchResult fetchResult;

    public QueryFetchSearchResult() {

    }

    public QueryFetchSearchResult(QuerySearchResult queryResult, FetchSearchResult fetchResult) {
        this.queryResult = queryResult;
        this.fetchResult = fetchResult;
    }

    @Override
    public long id() {
        return queryResult.id();
    }

    @Override
    public SearchShardTarget shardTarget() {
        return queryResult.shardTarget();
    }

    @Override
    public void shardTarget(SearchShardTarget shardTarget) {
        queryResult.shardTarget(shardTarget);
        fetchResult.shardTarget(shardTarget);
    }

    @Override
    public QuerySearchResult queryResult() {
        return queryResult;
    }

    @Override
    public FetchSearchResult fetchResult() {
        return fetchResult;
    }

    public static QueryFetchSearchResult readQueryFetchSearchResult(StreamInput in) throws IOException {
        QueryFetchSearchResult result = new QueryFetchSearchResult();
        result.readFrom(in);
        return result;
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        super.readFrom(in);
        queryResult = readQuerySearchResult(in);
        fetchResult = readFetchSearchResult(in);
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        queryResult.writeTo(out);
        fetchResult.writeTo(out);
    }
}
