<?php


namespace App\Http\Controllers\Api;


use App\Http\Controllers\Controller;
use App\Models\Suggestion;
use App\Models\SuggestionComment;
use App\Models\SuggestionRating;
use App\Models\User;
use Illuminate\Http\Request;

class UserController extends Controller
{
    private $user;


    public function __construct()
    {
        $this->middleware('auth:api');
        $this->user = auth('api')->user();
    }

    public function index()
    {
        return response()->json(['success' => true,'user'=>$this->user]);
    }

    public function leaveRating(Request $request)
    {
        if (is_null($rating = SuggestionRating::query()->where(['user_id' => $this->user->id, 'suggestion_id' => $request->post('suggestion_id')])->first())) {
            $rating = new SuggestionRating();
            $rating->user_id = $this->user->id;
            $rating->suggestion_id = $request->post('suggestion_id');
        } else {
            $rating->value = $request->post('value');
            $rating->datetime = date('Y-m-d H:i:s');
        }
        $rating->save();
        return response()->json(['success' => true]);
    }

    public function leaveComment(Request $request)
    {
        $rating = new SuggestionComment();
        $rating->user_id = $this->user->id;
        $rating->suggestion_id = $request->post('suggestion_id');
        $rating->text = $request->post('text');
        $rating->datetime = date('Y-m-d H:i:s');
        $rating->save();
        $comments = Suggestion::find($request->post('suggestion_id'))->comments()->orderBy('datetime','ASC')->with('user')->get();
        return response()->json(['success' => true,'comments'=>$comments]);
    }

}
