<?php

namespace App\Models;

use Backpack\CRUD\app\Models\Traits\CrudTrait;
use Illuminate\Database\Eloquent\Model;

class Suggestion extends Model
{
    use CrudTrait;

    /*
    |--------------------------------------------------------------------------
    | GLOBAL VARIABLES
    |--------------------------------------------------------------------------
    */

    protected $table = 'suggestions';
    protected $primaryKey = 'id';
    public $timestamps = false;
//    protected $guarded = ['id'];
    protected $fillable = [
        'title',
        'topic_id',
        'existing_solution_text',
        'existing_solution_image',
        'existing_solution_video',
        'proposed_solution_text',
        'proposed_solution_image',
        'proposed_solution_video',
        'positive_effect',
        'status_id',
        'registration_number',
        'author_id',
    ];
    // protected $hidden = [];
    // protected $dates = [];

    protected $appends = ['rating','experted'];
    /*
    |--------------------------------------------------------------------------
    | FUNCTIONS
    |--------------------------------------------------------------------------
    */

    /*
    |--------------------------------------------------------------------------
    | RELATIONS
    |--------------------------------------------------------------------------
    */

    public function statuss(){
        return $this->belongsTo(Status::class,'status_id');
    }

    public function author()
    {
        return $this->belongsTo(User::class);
    }

    public function topic()
    {
        return $this->belongsTo(Topic::class);
    }

    public function comments(){
        return $this->hasMany(SuggestionComment::class);
    }

    public function expertAssessments(){
        return $this->hasMany(ExpertAssessment::class);
    }

    public function getAcceptedCount(){
        return count(ExpertAssessment::query()->where(['suggestion_id'=>$this->id,'accepted'=>1])->get()->toArray());
    }
    public function getDeniedCount(){
        return count(ExpertAssessment::query()->where(['suggestion_id'=>$this->id,'accepted'=>0])->get()->toArray());
    }
    public function getExpertedAttribute(){
        return is_null(ExpertAssessment::query()->where(['suggestion_id'=>$this->id,'expert_id'=>auth('api')->user()->id])->first()) ? 0 : 1;
    }


    /*
    |--------------------------------------------------------------------------
    | SCOPES
    |--------------------------------------------------------------------------
    */

    /*
    |--------------------------------------------------------------------------
    | ACCESSORS
    |--------------------------------------------------------------------------
    */

    public function getRatingAttribute(){
        $value = intval(SuggestionRating::query()->where(['suggestion_id'=>$this->id])->get()->avg('value'),10);
        return is_null($value) ? 0 : $value;
    }
    /*
    |--------------------------------------------------------------------------
    | MUTATORS
    |--------------------------------------------------------------------------
    */
}
