/*
 *
 *  * Copyright 2016 Skymind,Inc.
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 */
package org.deeplearning4j.arbiter.evaluator.graph;

import org.deeplearning4j.arbiter.optimize.api.data.DataProvider;
import org.deeplearning4j.arbiter.optimize.api.evaluation.ModelEvaluator;
import org.deeplearning4j.datasets.iterator.DataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;

public class GraphClassificationDataSetEvaluator implements ModelEvaluator<ComputationGraph,DataSetIterator,Evaluation> {
    @Override
    public Evaluation evaluateModel(ComputationGraph model, DataProvider<DataSetIterator> dataProvider) {

        DataSetIterator iterator = dataProvider.testData(null);
        Evaluation eval = new Evaluation();
        while(iterator.hasNext()){
            DataSet ds = iterator.next();
            INDArray features = ds.getFeatures();
            INDArray labels = ds.getLabels();
            INDArray out = model.output(features)[0];
            //TODO: This won't work for time series (RNNs) + for masking
            eval.eval(labels,out);
        }

        return eval;
    }
}