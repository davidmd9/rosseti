<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\ExpertAssessment;
use App\Models\Suggestion;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Http\Response;

class SuggestionController extends Controller
{
    private $user;


    public function __construct()
    {
        $this->middleware('auth:api');
        $this->user = auth('api')->user();
    }


    /**
     * Display a listing of the resource.
     *
     * @return Response
     */
    public function index()
    {
        return \response()->json(['success'=>true,'suggestions'=>Suggestion::with(['author',
            'comments'=>function($query){
                $query->orderBy('datetime','ASC')->get();
            },
            'comments.user'])->get()]);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param Request $request
     * @return JsonResponse
     */
    public function store(Request $request)
    {
        $data = $request->except(['existing_solution_image','existing_solution_video','proposed_solution_image','proposed_solution_video']);
        $data['author_id'] = $this->user->id;
        $data['status_id'] = 1;
        $sug = new Suggestion();
        $sug->title = $request->get('title');
        $sug->author_id = $this->user->id;
        $sug->status_id = 1;
        $sug->topic_id = $request->get('topic_id');
        $sug->existing_solution_text = $request->get('existing_solution_text');
        $sug->proposed_solution_text = $request->get('proposed_solution_text');
        $sug->positive_effect = $request->get('positive_effect');
        $sug->save();

        if ($request->has('existing_solution_image')){
            $path = $this->uploadFile($request->file('existing_solution_image'));
            $sug->existing_solution_image=$path;
            $sug->save();
        }

        if ($request->has('existing_solution_video')){
            $path = $this->uploadFile($request->file('existing_solution_video'));
            $sug->existing_solution_video=$path;
            $sug->save();
        }
        if ($request->has('proposed_solution_image')){
            $path = $this->uploadFile($request->file('proposed_solution_image'));
            $sug->proposed_solution_image=$path;
            $sug->save();
        }

        if ($request->has('proposed_solution_video')){
            $path = $this->uploadFile($request->file('proposed_solution_video'));
            $sug->proposed_solution_video=$path;
            $sug->save();
        }
        return  response()->json(['success'=>true]);
    }

    public function uploadFile($file){
        $name = time() . round(explode(' ', microtime())[0] * 1000) . '.' . $file->getClientOriginalExtension();
        $file->move(public_path() . '/uploads', $name);
        $path = env('APP_URL') . '/uploads/' . $name;
        return $path;
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param Request $request
     * @param  int  $id
     * @return Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return Response
     */
    public function destroy($id)
    {
        //
    }

    public function storeExpertApprove(Request $request){
        $assessment = ExpertAssessment::query()->where(['expert_id'=>$this->user->id,'suggestion_id'=>$request->get('suggestion_id')])->first();
        if (is_null($assessment)){
            $assessment = new ExpertAssessment();
            $assessment->expert_id = $this->user->id;
            $assessment->suggestion_id = $request->get('suggestion_id');
        }
        $assessment->datetime = date('Y-m-d H:i:s');
        $assessment->accepted = $request->get('accepted');
        $assessment->save();

        return \response()->json(['success'=>true]);
    }
}
