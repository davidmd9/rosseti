<?php


namespace App\Http\Controllers\Api;


use App\Http\Controllers\Controller;
use App\Models\Topic;

class TopicController extends Controller
{
    private $user;

    public function __construct()
    {
        $this->middleware('auth:api');
        $this->user = auth('api')->user();
    }

    public function index(){

        return response()->json(['success'=>true,'topics'=>Topic::all()]);
    }
}
