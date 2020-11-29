<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::post('auth/phone',[\App\Http\Controllers\Api\AuthController::class,'auth']);

Route::post('auth/verify-code',[\App\Http\Controllers\Api\AuthController::class,'verifyCode']);


Route::post('suggestions/store',[\App\Http\Controllers\Api\SuggestionController::class,'store']);
Route::get('suggestions/index',[\App\Http\Controllers\Api\SuggestionController::class,'index']);
Route::post('suggestions/rating/store',[\App\Http\Controllers\Api\UserController::class,'leaveRating']);
Route::post('suggestions/comment/store',[\App\Http\Controllers\Api\UserController::class,'leaveComment']);
Route::post('suggestions/expert-judgment',[\App\Http\Controllers\Api\SuggestionController::class,'storeExpertApprove']);
Route::get('topics',[\App\Http\Controllers\Api\TopicController::class,'index']);

