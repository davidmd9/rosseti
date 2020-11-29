<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

/**
 * @property  int expert_id
 */
class ExpertAssessment extends Model
{
    use HasFactory;
    protected $table = 'expert_assessments';
    protected $primaryKey = 'id';
    public $timestamps = false;
    protected $fillable = [
        'expert_id',
        'accepted',
        'datetime',
        'suggestion_id'
    ];


    public function expert(){
        return $this->belongsTo(User::class);
    }
    public function suggestion(){
        return $this->belongsTo(Suggestion::class);
    }
}
