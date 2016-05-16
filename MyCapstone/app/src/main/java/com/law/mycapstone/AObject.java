/*===============================================================================
Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other 
countries.
===============================================================================*/

package com.law.mycapstone;


import java.nio.Buffer;

import SampleApplication.utils.MeshObject;


public class AObject extends MeshObject
{
    // Data for drawing the 3D plane as overlay
    private static final double letterVertices[] = { 1.819778, -0.050000,
            0.116930, 1.925000, 0.000000, 0.016930, 1.992688, -0.050000,
            0.116930, 1.786979, 0.000000, 0.016930, 0.183970, -0.050000,
            0.116930, 0.289064, 0.000000, 0.016930, 0.324123, -0.050000,
            0.116930, 0.184376, 0.000000, 0.016930, 0.920720, 2.250001,
            0.116931, 0.773439, 2.200001, 0.016930, 0.738394, 2.250001,
            0.116931, 0.931772, 2.200001, 0.016930, 1.521144, 1.483334,
            0.116931, 1.248438, 2.200001, 0.016930, 1.285373, 2.250001,
            0.116931, 1.473959, 1.466668, 0.016930, 1.521144, 1.483334,
            0.900000, 1.248438, 2.200001, 1.000000, 1.473959, 1.466668,
            1.000000, 1.285373, 2.250001, 0.900000, 1.925000, 0.000000,
            1.000000, 1.756916, 0.716667, 0.900000, 1.699480, 0.733333,
            1.000000, 1.992688, -0.050000, 0.900000, 1.756916, 0.716668,
            0.116930, 1.925000, 0.000000, 0.016930, 1.699480, 0.733334,
            0.016930, 1.992688, -0.050000, 0.116930, 0.920720, 2.250001,
            0.900000, 0.773439, 2.200001, 1.000000, 0.931772, 2.200001,
            1.000000, 0.738394, 2.250001, 0.900000, 1.248438, 2.200001,
            1.000000, 1.103046, 2.250001, 0.900000, 1.090105, 2.200001,
            1.000000, 1.285373, 2.250001, 0.900000, 1.103046, 2.250001,
            0.116931, 1.248438, 2.200001, 0.016930, 1.090105, 2.200001,
            0.016930, 1.285373, 2.250001, 0.116931, 0.181907, 0.716668,
            0.116930, -0.024999, 0.000000, 0.016930, -0.096336, -0.050000,
            0.116930, 0.241147, 0.733334, 0.016930, 0.181907, 0.716667,
            0.900000, -0.024999, 0.000000, 1.000000, 0.241147, 0.733333,
            1.000000, -0.096336, -0.050000, 0.900000, 0.773439, 2.200001,
            1.000000, 0.460151, 1.483334, 0.900000, 0.507293, 1.466668,
            1.000000, 0.738394, 2.250001, 0.900000, 0.460151, 1.483334,
            0.116931, 0.773439, 2.200001, 0.016930, 0.507293, 1.466668,
            0.016930, 0.738394, 2.250001, 0.116931, 0.183970, -0.050000,
            0.900000, 0.289064, 0.000000, 1.000000, 0.184376, 0.000000,
            1.000000, 0.324123, -0.050000, 0.900000, -0.024999, 0.000000,
            1.000000, 0.043817, -0.050000, 0.900000, 0.079689, 0.000000,
            1.000000, -0.096336, -0.050000, 0.900000, 0.043817, -0.050000,
            0.116930, -0.024999, 0.000000, 0.016930, 0.079689, 0.000000,
            0.016930, -0.096336, -0.050000, 0.116930, 1.320834, 0.450000,
            0.116930, 1.061980, 0.500000, 0.016930, 1.357813, 0.500000,
            0.016930, 1.049013, 0.450000, 0.116930, 0.505372, 0.450000,
            0.116930, 0.409897, 0.333333, 0.016930, 0.470314, 0.500000,
            0.016930, 0.444956, 0.283333, 0.116930, 0.409897, 0.333333,
            1.000000, 0.505372, 0.450000, 0.900000, 0.470314, 0.500000,
            1.000000, 0.444956, 0.283333, 0.900000, 0.289064, 0.000000,
            1.000000, 0.384539, 0.116667, 0.900000, 0.349480, 0.166667,
            1.000000, 0.324123, -0.050000, 0.900000, 0.384539, 0.116667,
            0.116930, 0.289064, 0.000000, 0.016930, 0.349480, 0.166667,
            0.016930, 0.324123, -0.050000, 0.116930, 1.061980, 0.500000,
            1.000000, 1.320834, 0.450000, 0.900000, 1.357813, 0.500000,
            1.000000, 1.049013, 0.450000, 0.900000, 0.505372, 0.450000,
            0.900000, 0.766147, 0.500000, 1.000000, 0.470314, 0.500000,
            1.000000, 0.777193, 0.450000, 0.900000, 0.766147, 0.500000,
            0.016930, 0.505372, 0.450000, 0.116930, 0.470314, 0.500000,
            0.016930, 0.777193, 0.450000, 0.116930, 1.422917, 0.116667,
            0.116930, 1.510938, 0.000000, 0.016930, 1.473958, -0.050000,
            0.116930, 1.459896, 0.166667, 0.016930, 1.422917, 0.116667,
            0.900000, 1.510938, 0.000000, 1.000000, 1.459896, 0.166667,
            1.000000, 1.473958, -0.050000, 0.900000, 1.320834, 0.450000,
            0.900000, 1.408855, 0.333333, 1.000000, 1.357813, 0.500000,
            1.000000, 1.371875, 0.283333, 0.900000, 1.408855, 0.333333,
            0.016930, 1.320834, 0.450000, 0.116930, 1.357813, 0.500000,
            0.016930, 1.371875, 0.283333, 0.116930, 1.819778, -0.050000,
            0.900000, 1.925000, 0.000000, 1.000000, 1.786979, 0.000000,
            1.000000, 1.992688, -0.050000, 0.900000, 1.510938, 0.000000,
            1.000000, 1.646868, -0.050000, 0.900000, 1.648959, 0.000000,
            1.000000, 1.473958, -0.050000, 0.900000, 1.646868, -0.050000,
            0.116930, 1.510938, 0.000000, 0.016930, 1.648959, 0.000000,
            0.016930, 1.473958, -0.050000, 0.116930, 1.196388, 0.850001,
            0.116930, 1.159376, 1.140626, 0.016930, 1.264063, 0.800001,
            0.016930, 1.113702, 1.151444, 0.116931, 0.650990, 0.850001,
            0.116930, 0.703126, 1.140626, 0.016930, 0.750103, 1.151444,
            0.116931, 0.579689, 0.800001, 0.016930, 0.832789, 0.850001,
            0.116930, 1.035939, 0.800001, 0.016930, 0.807814, 0.800001,
            0.016930, 1.014589, 0.850001, 0.116930, 1.035939, 0.800001,
            1.000000, 0.832789, 0.850001, 0.900000, 0.807814, 0.800001,
            1.000000, 1.014589, 0.850001, 0.900000, 1.196388, 0.850001,
            0.900000, 1.264063, 0.800001, 1.000000, 1.196388, 0.850001,
            0.116930, 1.264063, 0.800001, 0.016930, 0.650990, 0.850001,
            0.900000, 0.703126, 1.140626, 1.000000, 0.579689, 0.800001,
            1.000000, 0.750103, 1.151444, 0.900000, 0.650990, 0.850001,
            0.900000, 0.579689, 0.800001, 1.000000, 0.650990, 0.850001,
            0.116930, 0.579689, 0.800001, 0.016930, 0.948329, 1.754329,
            0.116931, 0.826563, 1.481251, 0.016930, 0.950001, 1.821876,
            0.016930, 0.849216, 1.452887, 0.116931, 0.826563, 1.481251,
            1.000000, 0.948329, 1.754329, 0.900000, 0.950001, 1.821876,
            1.000000, 0.849216, 1.452887, 0.900000, 1.159376, 1.140626,
            1.000000, 1.196388, 0.850001, 0.900000, 1.264063, 0.800001,
            1.000000, 1.113702, 1.151444, 0.900000, 0.948329, 1.754329,
            0.900000, 1.054688, 1.481251, 1.000000, 0.950001, 1.821876,
            1.000000, 1.031016, 1.452887, 0.900000, 1.054688, 1.481251,
            0.016930, 0.948329, 1.754329, 0.116931, 0.950001, 1.821876,
            0.016930, 1.031016, 1.452887, 0.116931, 0.079689, 0.000000,
            1.000000, 0.241147, 0.733333, 1.000000, -0.024999, 0.000000,
            1.000000, 1.699480, 0.733333, 1.000000, 1.786979, 0.000000,
            1.000000, 1.925000, 0.000000, 1.000000, 0.184376, 0.000000,
            1.000000, 1.648959, 0.000000, 1.000000, 0.289064, 0.000000,
            1.000000, 0.349480, 0.166667, 1.000000, 0.409897, 0.333333,
            1.000000, 0.470314, 0.500000, 1.000000, 1.510938, 0.000000,
            1.000000, 1.459896, 0.166667, 1.000000, 1.408855, 0.333333,
            1.000000, 1.357813, 0.500000, 1.000000, 1.090105, 2.200001,
            1.000000, 1.473959, 1.466668, 1.000000, 1.248438, 2.200001,
            1.000000, 1.264063, 0.800001, 1.000000, 1.159376, 1.140626,
            1.000000, 1.054688, 1.481251, 1.000000, 0.950001, 1.821876,
            1.000000, 0.931772, 2.200001, 1.000000, 1.061980, 0.500000,
            1.000000, 1.035939, 0.800001, 1.000000, 0.766147, 0.500000,
            1.000000, 0.807814, 0.800001, 1.000000, 0.579689, 0.800001,
            1.000000, 0.507293, 1.466668, 1.000000, 0.703126, 1.140626,
            1.000000, 0.826563, 1.481251, 1.000000, 0.773439, 2.200001,
            1.000000, 0.241147, 0.733334, 0.016930, 0.079689, 0.000000,
            0.016930, -0.024999, 0.000000, 0.016930, 1.786979, 0.000000,
            0.016930, 1.699480, 0.733334, 0.016930, 1.925000, 0.000000,
            0.016930, 0.184376, 0.000000, 0.016930, 1.648959, 0.000000,
            0.016930, 0.289064, 0.000000, 0.016930, 0.349480, 0.166667,
            0.016930, 0.409897, 0.333333, 0.016930, 0.470314, 0.500000,
            0.016930, 1.510938, 0.000000, 0.016930, 1.459896, 0.166667,
            0.016930, 1.408855, 0.333333, 0.016930, 1.357813, 0.500000,
            0.016930, 1.473959, 1.466668, 0.016930, 1.264063, 0.800001,
            0.016930, 1.159376, 1.140626, 0.016930, 1.054688, 1.481251,
            0.016930, 1.248438, 2.200001, 0.016930, 0.950001, 1.821876,
            0.016930, 1.090105, 2.200001, 0.016930, 0.931772, 2.200001,
            0.016930, 1.061980, 0.500000, 0.016930, 1.035939, 0.800001,
            0.016930, 0.766147, 0.500000, 0.016930, 0.807814, 0.800001,
            0.016930, 0.579689, 0.800001, 0.016930, 0.507293, 1.466668,
            0.016930, 0.703126, 1.140626, 0.016930, 0.826563, 1.481251,
            0.016930, 0.773439, 2.200001, 0.016930 };
    
    private static final double letterNormals[] = { 0.000000, -0.961331,
            -0.275397, 0.615430, -0.454612, -0.643874, 0.586155, -0.756698,
            -0.289536, 0.000000, -0.894427, -0.447214, 0.000000, -0.952226,
            -0.305393, 0.489973, -0.698788, -0.521173, 0.438404, -0.871395,
            -0.220167, 0.000000, -0.894427, -0.447214, 0.000000, 0.952226,
            -0.305393, -0.489859, 0.698905, -0.521123, -0.438293, 0.871457,
            -0.220141, 0.000000, 0.894428, -0.447212, 0.910160, 0.279900,
            -0.305393, 0.504613, 0.683118, -0.527935, 0.691854, 0.686518,
            -0.223676, 0.854914, 0.262910, -0.447214, 0.930254, 0.286079,
            0.229753, 0.504613, 0.683118, 0.527935, 0.854914, 0.262910,
            0.447214, 0.452825, 0.863087, 0.223676, 0.615430, -0.454612,
            0.643874, 0.918862, 0.282576, 0.275396, 0.854914, 0.262910,
            0.447213, 0.895565, -0.337835, 0.289535, 0.937127, 0.288193,
            -0.196819, 0.615430, -0.454612, -0.643874, 0.854914, 0.262910,
            -0.447213, 0.586155, -0.756698, -0.289536, 0.000000, 0.973249,
            0.229753, -0.489859, 0.698905, 0.521123, 0.000000, 0.894428,
            0.447213, -0.669651, 0.709299, 0.220141, 0.504613, 0.683118,
            0.527935, 0.000000, 0.961331, 0.275397, 0.000000, 0.894427,
            0.447213, 0.452825, 0.863087, 0.223676, 0.000000, 0.980440,
            -0.196819, 0.504613, 0.683118, -0.527935, 0.000000, 0.894428,
            -0.447213, 0.691854, 0.686518, -0.223676, -0.903658, 0.327961,
            -0.275397, -0.617452, -0.432769, -0.656859, -0.905657, -0.301904,
            -0.297725, -0.840768, 0.305137, -0.447214, -0.921621, 0.334480,
            0.196819, -0.617452, -0.432769, 0.656859, -0.840768, 0.305137,
            0.447214, -0.592760, -0.748329, 0.297724, -0.489859, 0.698905,
            0.521123, -0.895100, 0.324855, 0.305393, -0.840768, 0.305137,
            0.447214, -0.669651, 0.709299, 0.220141, -0.914862, 0.332027,
            -0.229753, -0.489859, 0.698905, -0.521123, -0.840768, 0.305137,
            -0.447214, -0.438293, 0.871457, -0.220141, 0.000000, -0.973249,
            0.229753, 0.489974, -0.698788, 0.521173, 0.000000, -0.894427,
            0.447214, 0.669822, -0.709130, 0.220166, -0.617452, -0.432769,
            0.656859, 0.000000, -0.961331, 0.275397, 0.000000, -0.894427,
            0.447214, -0.592760, -0.748329, 0.297724, 0.000000, -0.980440,
            -0.196819, -0.617452, -0.432769, -0.656859, 0.000000, -0.894427,
            -0.447214, -0.905657, -0.301904, -0.297725, -0.474959, -0.820302,
            -0.318620, 0.000000, -0.894427, -0.447214, -0.504951, -0.682741,
            -0.528100, 0.000000, -0.961331, -0.275397, 0.624484, -0.715307,
            -0.313616, 0.840883, -0.304820, -0.447214, 0.489973, -0.698788,
            -0.521173, 0.903781, -0.327621, -0.275397, 0.840883, -0.304820,
            0.447214, 0.459663, -0.830876, 0.313616, 0.489973, -0.698788,
            0.521173, 0.921747, -0.334133, 0.196819, 0.489974, -0.698788,
            0.521173, 0.903782, -0.327620, 0.275397, 0.840883, -0.304820,
            0.447214, 0.669822, -0.709130, 0.220166, 0.921747, -0.334133,
            -0.196819, 0.489973, -0.698788, -0.521173, 0.840883, -0.304820,
            -0.447214, 0.438404, -0.871395, -0.220167, 0.000000, -0.894427,
            0.447214, -0.645266, -0.694344, 0.318619, -0.504951, -0.682741,
            0.528100, 0.000000, -0.980440, 0.196819, 0.459663, -0.830876,
            0.313616, 0.000000, -0.894427, 0.447214, 0.489973, -0.698788,
            0.521173, 0.000000, -0.973249, 0.229753, 0.000000, -0.894427,
            -0.447214, 0.624484, -0.715307, -0.313616, 0.489973, -0.698788,
            -0.521173, 0.000000, -0.988273, -0.152697, -0.919192, -0.281501,
            -0.275397, -0.504951, -0.682740, -0.528100, -0.692368, -0.685972,
            -0.223762, -0.855221, -0.261910, -0.447214, -0.937463, -0.287097,
            0.196819, -0.504951, -0.682741, 0.528100, -0.855221, -0.261910,
            0.447214, -0.453161, -0.862888, 0.223762, -0.645266, -0.694344,
            0.318619, -0.855221, -0.261910, 0.447214, -0.504951, -0.682741,
            0.528100, -0.919192, -0.281502, 0.275397, -0.855221, -0.261910,
            -0.447214, -0.474959, -0.820302, -0.318620, -0.504951, -0.682741,
            -0.528100, -0.937463, -0.287097, -0.196819, 0.000000, -0.980440,
            0.196819, 0.615430, -0.454612, 0.643874, 0.000000, -0.894427,
            0.447214, 0.895565, -0.337835, 0.289535, -0.504951, -0.682741,
            0.528100, 0.000000, -0.952226, 0.305393, 0.000000, -0.894427,
            0.447213, -0.453161, -0.862888, 0.223762, 0.000000, -0.973249,
            -0.229753, -0.504951, -0.682740, -0.528100, 0.000000, -0.894427,
            -0.447213, -0.692368, -0.685972, -0.223762, -0.749439, 0.561236,
            -0.351219, -0.878209, -0.260081, -0.401380, -0.615420, 0.454687,
            -0.643830, -0.940790, -0.264010, -0.212635, 0.722737, 0.509889,
            -0.466544, 0.864154, -0.303317, -0.401542, 0.937656, -0.312547,
            -0.152036, 0.617439, 0.432980, -0.656732, 0.000000, 0.980440,
            -0.196819, 0.000000, 0.894427, -0.447213, 0.000000, 0.894427,
            -0.447214, 0.000000, 0.973249, -0.229753, 0.000000, 0.894427,
            0.447213, 0.000000, 0.961331, 0.275397, 0.000000, 0.894427,
            0.447214, 0.000000, 0.988273, 0.152696, -0.714801, 0.531798,
            0.454148, -0.615420, 0.454687, 0.643830, -0.749439, 0.561236,
            -0.351219, -0.615420, 0.454687, -0.643830, 0.760021, 0.539858,
            0.361830, 0.864154, -0.303317, 0.401542, 0.617439, 0.432980,
            0.656732, 0.926425, -0.310553, 0.212822, 0.760021, 0.539858,
            0.361830, 0.617439, 0.432980, 0.656732, 0.722737, 0.509889,
            -0.466544, 0.617439, 0.432980, -0.656732, 0.367794, -0.836636,
            -0.405916, 0.903956, -0.312284, -0.292134, -0.020501, -0.828336,
            -0.559857, 0.939658, -0.308956, -0.146930, 0.903956, -0.312284,
            0.292134, -0.409086, -0.817238, 0.405919, -0.020500, -0.828336,
            0.559857, 0.945411, -0.310847, 0.097833, -0.878209, -0.260081,
            0.401380, -0.714801, 0.531798, 0.454148, -0.615420, 0.454687,
            0.643830, -0.952094, -0.265411, 0.151902, -0.409086, -0.817238,
            0.405919, -0.918417, -0.266991, 0.291934, -0.020500, -0.828336,
            0.559857, -0.953941, -0.261668, 0.146722, -0.918417, -0.266991,
            -0.291934, 0.367794, -0.836636, -0.405916, -0.020501, -0.828336,
            -0.559857, -0.959764, -0.263265, -0.097695, 0.000000, -0.000000,
            1.000000, 0.000000, -0.000000, 1.000000, 0.000000, -0.000000,
            1.000000, 0.000000, -0.000000, 1.000000, 0.000000, -0.000000,
            1.000000, 0.000000, -0.000000, 1.000000, 0.000000, -0.000000,
            1.000000, 0.000000, -0.000000, 1.000000, 0.000000, -0.000000,
            1.000000, 0.000000, -0.000000, 1.000000, 0.000000, -0.000000,
            1.000000, 0.000000, -0.000000, 1.000000, 0.000000, -0.000000,
            1.000000, 0.000000, -0.000000, 1.000000, 0.000000, -0.000000,
            1.000000, 0.000000, -0.000000, 1.000000, 0.000000, -0.000000,
            1.000000, 0.000000, -0.000000, 1.000000, 0.000000, -0.000000,
            1.000000, 0.000000, -0.000000, 1.000000, 0.000000, -0.000000,
            1.000000, 0.000000, -0.000000, 1.000000, 0.000000, -0.000000,
            1.000000, 0.000000, -0.000000, 1.000000, 0.000000, -0.000000,
            1.000000, 0.000000, -0.000000, 1.000000, 0.000000, -0.000000,
            1.000000, 0.000000, -0.000000, 1.000000, 0.000000, -0.000000,
            1.000000, 0.000000, -0.000000, 1.000000, 0.000000, -0.000000,
            1.000000, 0.000000, -0.000000, 1.000000, 0.000000, -0.000000,
            1.000000, 0.000000, 0.000000, -1.000000, 0.000000, 0.000000,
            -1.000000, 0.000000, 0.000000, -1.000000, 0.000000, 0.000000,
            -1.000000, 0.000000, 0.000000, -1.000000, 0.000000, 0.000000,
            -1.000000, 0.000000, 0.000000, -1.000000, 0.000000, 0.000000,
            -1.000000, 0.000000, 0.000000, -1.000000, 0.000000, 0.000000,
            -1.000000, 0.000000, 0.000000, -1.000000, 0.000000, 0.000000,
            -1.000000, 0.000000, 0.000000, -1.000000, 0.000000, 0.000000,
            -1.000000, 0.000000, 0.000000, -1.000000, 0.000000, 0.000000,
            -1.000000, 0.000000, 0.000000, -1.000000, 0.000000, 0.000000,
            -1.000000, 0.000000, 0.000000, -1.000000, 0.000000, 0.000000,
            -1.000000, 0.000000, 0.000000, -1.000000, 0.000000, 0.000000,
            -1.000000, 0.000000, 0.000000, -1.000000, 0.000000, 0.000000,
            -1.000000, 0.000000, 0.000000, -1.000000, 0.000000, 0.000000,
            -1.000000, 0.000000, 0.000000, -1.000000, 0.000000, 0.000000,
            -1.000000, 0.000000, 0.000000, -1.000000, 0.000000, 0.000000,
            -1.000000, 0.000000, 0.000000, -1.000000, 0.000000, 0.000000,
            -1.000000, 0.000000, 0.000000, -1.000000 };
    
    private static final double letterTexcoords[] = { 0.047899, 0.698999,
            0.005000, 0.663441, 0.020844, 0.640566, 0.005000, 0.710084,
            0.047899, 0.900273, 0.005000, 0.864758, 0.020844, 0.852910,
            0.005000, 0.900136, 0.221020, 0.940771, 0.178120, 0.984577,
            0.193964, 0.995000, 0.178120, 0.937484, 0.884115, 0.166160,
            0.841964, 0.015510, 0.857531, 0.005000, 0.841964, 0.169663,
            0.926266, 0.166160, 0.995000, 0.015510, 0.995000, 0.169663,
            0.979433, 0.005000, 0.995000, 0.477969, 0.952849, 0.327319,
            0.995000, 0.323816, 0.979433, 0.488479, 0.910698, 0.327319,
            0.841964, 0.477969, 0.841964, 0.323816, 0.857531, 0.488479,
            0.263919, 0.940771, 0.333874, 0.984577, 0.333874, 0.937484,
            0.318030, 0.995000, 0.333874, 0.843299, 0.290975, 0.886542,
            0.333874, 0.890391, 0.318030, 0.832313, 0.248075, 0.886542,
            0.178120, 0.843299, 0.178120, 0.890391, 0.193964, 0.832313,
            0.408002, 0.833840, 0.351241, 0.984490, 0.372204, 0.995000,
            0.351241, 0.830337, 0.464764, 0.833840, 0.557324, 0.984490,
            0.557324, 0.830337, 0.536361, 0.995000, 0.557324, 0.522031,
            0.500562, 0.672681, 0.557324, 0.676184, 0.536361, 0.511521,
            0.443801, 0.672681, 0.351241, 0.522031, 0.351241, 0.676184,
            0.372204, 0.511521, 0.090799, 0.900273, 0.160753, 0.864758,
            0.160753, 0.900136, 0.144910, 0.852910, 0.160753, 0.970892,
            0.117854, 0.947637, 0.160753, 0.935514, 0.144910, 0.995000,
            0.074955, 0.947637, 0.005000, 0.970892, 0.005000, 0.935514,
            0.020844, 0.995000, 0.191220, 0.350554, 0.175100, 0.427545,
            0.175100, 0.339556, 0.218748, 0.433044, 0.193964, 0.650997,
            0.178120, 0.685697, 0.178120, 0.636125, 0.221020, 0.700568,
            0.333874, 0.685697, 0.318030, 0.650997, 0.333874, 0.636125,
            0.263919, 0.700568, 0.333874, 0.784839, 0.290975, 0.750139,
            0.333874, 0.735268, 0.318030, 0.799711, 0.248075, 0.750139,
            0.178120, 0.784839, 0.178120, 0.735268, 0.193964, 0.799711,
            0.333571, 0.427545, 0.317451, 0.350554, 0.333571, 0.339556,
            0.262395, 0.433044, 0.317451, 0.593095, 0.333571, 0.515534,
            0.333571, 0.603522, 0.289923, 0.513070, 0.175100, 0.515534,
            0.191220, 0.593095, 0.175100, 0.603522, 0.246275, 0.513070,
            0.047151, 0.532255, 0.005000, 0.582142, 0.020567, 0.603522,
            0.005000, 0.510875, 0.089302, 0.532255, 0.158036, 0.582142,
            0.158036, 0.510875, 0.142469, 0.603522, 0.142469, 0.389721,
            0.158036, 0.439608, 0.158036, 0.368341, 0.115885, 0.460988,
            0.005000, 0.439608, 0.020567, 0.389721, 0.005000, 0.368341,
            0.073734, 0.460988, 0.090799, 0.698999, 0.160753, 0.663441,
            0.160753, 0.710084, 0.144910, 0.640566, 0.160753, 0.803369,
            0.117854, 0.757433, 0.160753, 0.756726, 0.144910, 0.815866,
            0.074955, 0.757433, 0.005000, 0.803369, 0.005000, 0.756726,
            0.020844, 0.815866, 0.182884, 0.277210, 0.228126, 0.306953,
            0.175100, 0.306953, 0.232017, 0.226419, 0.687431, 0.466096,
            0.671864, 0.335995, 0.740598, 0.324804, 0.671864, 0.488479,
            0.073734, 0.208679, 0.005000, 0.126376, 0.005000, 0.223923,
            0.047151, 0.133223, 0.158036, 0.126376, 0.115885, 0.208679,
            0.158036, 0.223923, 0.089302, 0.133223, 0.142469, 0.057768,
            0.158036, 0.028830, 0.020567, 0.057768, 0.005000, 0.028830,
            0.809333, 0.466096, 0.824900, 0.335995, 0.824900, 0.488479,
            0.782749, 0.324804, 0.142469, 0.290981, 0.158036, 0.321469,
            0.020567, 0.290981, 0.005000, 0.321469, 0.722876, 0.128953,
            0.671864, 0.183512, 0.671864, 0.031028, 0.722876, 0.226879,
            0.824900, 0.183512, 0.773888, 0.128953, 0.824900, 0.031028,
            0.773888, 0.226879, 0.228126, 0.014561, 0.182884, 0.044303,
            0.175100, 0.014561, 0.232017, 0.145885, 0.300124, 0.112025,
            0.281151, 0.014561, 0.334177, 0.014561, 0.266071, 0.112025,
            0.281151, 0.306953, 0.300124, 0.209489, 0.334177, 0.306953,
            0.266071, 0.209489, 0.602249, 0.995000, 0.636095, 0.834173,
            0.580303, 0.995000, 0.941809, 0.834173, 0.960151, 0.995000,
            0.989085, 0.995000, 0.624195, 0.995000, 0.931218, 0.995000,
            0.646140, 0.995000, 0.658806, 0.958448, 0.671471, 0.921897,
            0.684136, 0.885345, 0.902284, 0.995000, 0.891584, 0.958448,
            0.880885, 0.921897, 0.870184, 0.885345, 0.814064, 0.512519,
            0.894532, 0.673346, 0.847256, 0.512519, 0.850531, 0.819552,
            0.828586, 0.744850, 0.806640, 0.670148, 0.784694, 0.595445,
            0.780873, 0.512519, 0.808168, 0.885345, 0.802709, 0.819552,
            0.746152, 0.885345, 0.754887, 0.819552, 0.707065, 0.819552,
            0.691888, 0.673346, 0.732941, 0.744850, 0.758817, 0.670148,
            0.747681, 0.512519, 0.392672, 0.327652, 0.367538, 0.488479,
            0.351241, 0.488479, 0.633314, 0.488479, 0.619693, 0.327652,
            0.654800, 0.488479, 0.383835, 0.488479, 0.611828, 0.488479,
            0.400131, 0.488479, 0.409537, 0.451927, 0.418942, 0.415376,
            0.428347, 0.378824, 0.590343, 0.488479, 0.582397, 0.451927,
            0.574451, 0.415376, 0.566505, 0.378824, 0.584586, 0.166825,
            0.551911, 0.313031, 0.535614, 0.238329, 0.519317, 0.163626,
            0.549479, 0.005998, 0.503021, 0.088924, 0.524831, 0.005998,
            0.500183, 0.005998, 0.520452, 0.378824, 0.516399, 0.313031,
            0.474400, 0.378824, 0.480886, 0.313031, 0.445374, 0.313031,
            0.434104, 0.166825, 0.464589, 0.238329, 0.483805, 0.163626,
            0.475535, 0.005998 };
    
    private static final short letterIndices[] = { 0, 1, 2, 3, 1, 0, 4, 5, 6,
            7, 5, 4, 8, 9, 10, 11, 9, 8, 12, 13, 14, 15, 13, 12, 16, 17, 18,
            19, 17, 16, 20, 21, 22, 23, 21, 20, 22, 16, 18, 21, 16, 22, 24, 25,
            26, 27, 25, 24, 24, 23, 27, 21, 23, 24, 16, 14, 19, 12, 14, 16, 16,
            24, 12, 21, 24, 16, 12, 26, 15, 24, 26, 12, 28, 29, 30, 31, 29, 28,
            32, 33, 34, 35, 33, 32, 34, 28, 30, 33, 28, 34, 36, 37, 38, 39, 37,
            36, 36, 35, 39, 33, 35, 36, 28, 10, 31, 8, 10, 28, 28, 36, 8, 33,
            36, 28, 8, 38, 11, 36, 38, 8, 40, 41, 42, 43, 41, 40, 44, 45, 46,
            47, 45, 44, 48, 49, 50, 51, 49, 48, 49, 46, 50, 44, 46, 49, 52, 53,
            54, 55, 53, 52, 52, 51, 55, 49, 51, 52, 44, 42, 47, 40, 42, 44, 44,
            52, 40, 49, 52, 44, 43, 52, 54, 40, 52, 43, 56, 57, 58, 59, 57, 56,
            60, 61, 62, 63, 61, 60, 62, 56, 58, 61, 56, 62, 64, 65, 66, 67, 65,
            64, 64, 63, 67, 61, 63, 64, 56, 6, 59, 4, 6, 56, 56, 64, 4, 61, 64,
            56, 4, 66, 7, 64, 66, 4, 68, 69, 70, 71, 69, 68, 72, 73, 74, 75,
            73, 72, 76, 77, 78, 79, 77, 76, 80, 81, 82, 83, 81, 80, 82, 79, 76,
            81, 79, 82, 84, 85, 86, 87, 85, 84, 84, 83, 87, 81, 83, 84, 79, 72,
            77, 75, 72, 79, 79, 84, 75, 81, 84, 79, 75, 86, 73, 84, 86, 75, 88,
            89, 90, 91, 89, 88, 92, 93, 94, 95, 93, 92, 93, 91, 88, 95, 91, 93,
            96, 97, 98, 99, 97, 96, 99, 92, 97, 95, 92, 99, 91, 68, 89, 71, 68,
            91, 91, 99, 71, 95, 99, 91, 71, 96, 69, 99, 96, 71, 100, 101, 102,
            103, 101, 100, 104, 105, 106, 107, 105, 104, 108, 109, 110, 111,
            109, 108, 111, 106, 109, 104, 106, 111, 112, 113, 114, 115, 113,
            112, 115, 108, 113, 111, 108, 115, 104, 102, 107, 100, 102, 104,
            104, 115, 100, 111, 115, 104, 103, 115, 112, 100, 115, 103, 116,
            117, 118, 119, 117, 116, 120, 121, 122, 123, 121, 120, 121, 118,
            122, 116, 118, 121, 124, 125, 126, 127, 125, 124, 124, 123, 127,
            121, 123, 124, 116, 2, 119, 0, 2, 116, 116, 124, 0, 121, 124, 116,
            3, 124, 126, 0, 124, 3, 128, 129, 130, 131, 129, 128, 132, 133,
            134, 135, 133, 132, 136, 137, 138, 139, 137, 136, 140, 141, 142,
            143, 141, 140, 144, 140, 145, 143, 140, 144, 143, 136, 141, 139,
            136, 143, 143, 146, 139, 144, 146, 143, 137, 146, 147, 139, 146,
            137, 148, 149, 150, 151, 149, 148, 142, 152, 153, 141, 152, 142,
            148, 134, 151, 132, 134, 148, 152, 136, 154, 141, 136, 152, 154,
            138, 155, 136, 138, 154, 156, 157, 158, 159, 157, 156, 160, 161,
            162, 163, 161, 160, 151, 160, 149, 163, 160, 151, 163, 156, 161,
            159, 156, 163, 163, 134, 159, 151, 134, 163, 157, 134, 133, 159,
            134, 157, 164, 165, 166, 167, 165, 164, 168, 169, 170, 171, 169,
            168, 169, 167, 164, 171, 167, 169, 172, 173, 174, 175, 173, 172,
            175, 168, 173, 171, 168, 175, 167, 128, 165, 131, 128, 167, 167,
            175, 131, 171, 175, 167, 131, 172, 129, 175, 172, 131, 176, 177,
            178, 179, 180, 181, 182, 177, 176, 179, 183, 180, 184, 177, 182,
            185, 177, 184, 186, 177, 185, 187, 177, 186, 179, 188, 183, 179,
            189, 188, 179, 190, 189, 179, 191, 190, 192, 193, 194, 193, 191,
            179, 193, 195, 191, 193, 196, 195, 193, 197, 196, 192, 197, 193,
            192, 198, 197, 199, 198, 192, 195, 200, 191, 201, 200, 195, 201,
            202, 200, 203, 202, 201, 204, 202, 203, 204, 187, 202, 204, 177,
            187, 204, 205, 177, 206, 205, 204, 207, 205, 206, 207, 208, 205,
            198, 208, 207, 199, 208, 198, 209, 210, 211, 212, 213, 214, 209,
            215, 210, 216, 213, 212, 209, 217, 215, 209, 218, 217, 209, 219,
            218, 209, 220, 219, 221, 213, 216, 222, 213, 221, 223, 213, 222,
            224, 213, 223, 224, 225, 213, 226, 225, 224, 227, 225, 226, 228,
            225, 227, 228, 229, 225, 230, 229, 228, 230, 231, 229, 230, 232,
            231, 233, 226, 224, 233, 234, 226, 235, 234, 233, 235, 236, 234,
            235, 237, 236, 220, 237, 235, 209, 237, 220, 238, 237, 209, 238,
            239, 237, 238, 240, 239, 241, 240, 238, 241, 230, 240, 230, 241,
            232 };
    
    Buffer mVertBuff;
    Buffer mTexCoordBuff;
    Buffer mNormBuff;
    Buffer mIndBuff;
    
    
    public AObject()
    {
        mVertBuff = fillBuffer(letterVertices);
        mTexCoordBuff = fillBuffer(letterTexcoords);
        mNormBuff = fillBuffer(letterNormals);
        mIndBuff = fillBuffer(letterIndices);
    }
    
    
    @Override
    public Buffer getBuffer(BUFFER_TYPE bufferType)
    {
        Buffer result = null;
        switch (bufferType)
        {
            case BUFFER_TYPE_VERTEX:
                result = mVertBuff;
                break;
            case BUFFER_TYPE_TEXTURE_COORD:
                result = mTexCoordBuff;
                break;
            case BUFFER_TYPE_INDICES:
                result = mIndBuff;
                break;
            case BUFFER_TYPE_NORMALS:
                result = mNormBuff;
            default:
                break;
        }
        return result;
    }
    
    
    @Override
    public int getNumObjectVertex()
    {
        return letterVertices.length / 3;
    }
    
    
    @Override
    public int getNumObjectIndex()
    {
        return letterIndices.length;
    }
}
