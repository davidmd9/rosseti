<?php

namespace App\Http\Controllers\Admin;

use App\Http\Requests\SuggestionRequest;
use App\Models\Status;
use App\Models\Suggestion;
use App\Models\Topic;
use Backpack\CRUD\app\Http\Controllers\CrudController;
use Backpack\CRUD\app\Library\CrudPanel\CrudPanelFacade as CRUD;

/**
 * Class SuggestionCrudController
 * @package App\Http\Controllers\Admin
 * @property-read \Backpack\CRUD\app\Library\CrudPanel\CrudPanel $crud
 */
class SuggestionCrudController extends CrudController
{
    use \Backpack\CRUD\app\Http\Controllers\Operations\ListOperation;
    use \Backpack\CRUD\app\Http\Controllers\Operations\CreateOperation;
    use \Backpack\CRUD\app\Http\Controllers\Operations\UpdateOperation;
    use \Backpack\CRUD\app\Http\Controllers\Operations\DeleteOperation;
    use \Backpack\CRUD\app\Http\Controllers\Operations\ShowOperation;

    /**
     * Configure the CrudPanel object. Apply settings to all operations.
     *
     * @return void
     */
    public function setup()
    {
        CRUD::setModel(\App\Models\Suggestion::class);
        CRUD::setRoute(config('backpack.base.route_prefix') . '/suggestion');
        CRUD::setEntityNameStrings('', 'рацпредложения');
    }

    /**
     * Define what happens when the List operation is loaded.
     *
     * @see  https://backpackforlaravel.com/docs/crud-operation-list-entries
     * @return void
     */
    protected function setupListOperation()
    {
        // select2 filter
        $this->crud->addFilter([
            'name' => 'topic_id',
            'type' => 'select2',
            'label' => 'Направление деятельности'
        ], function () {
            return Topic::all()->pluck('title', 'id')->toArray();
        }, function ($value) { // if the filter is active
            $this->crud->addClause('where', 'topic_id', $value);
        });

        // select2 filter
        $this->crud->addFilter([
            'name' => 'status_id',
            'type' => 'select2',
            'label' => 'Статус'
        ], function () {
            return Status::all()->pluck('title', 'id')->toArray();
        }, function ($value) { // if the filter is active
            $this->crud->addClause('where', 'status_id', $value);
        });

        CRUD::addColumns([
            [
                // 1-n relationship
                'type' => 'select',
                'label'=>'Автор',
                'name' => 'author_id', // the column that contains the ID of that connected entity;
                'entity' => 'author', // the method that defines the relationship in your Model
                'attribute' => 'full_name', // foreign key attribute that is shown to user
                'model' => "App\Models\User", // foreign key model
            ],
            [
                // 1-n relationship
                'type' => 'select',
                'label'=>'Напрвление деятельности',
                'name' => 'topic_id', // the column that contains the ID of that connected entity;
                'entity' => 'topic', // the method that defines the relationship in your Model
                'attribute' => 'title', // foreign key attribute that is shown to user
                'model' => "App\Models\Topic", // foreign key model
            ],
            [
                // 1-n relationship
                'type' => 'text',
                'name' => 'title',
                'label'=>'Название',// the column that contains the ID of that connected entity;
            ],
            [
                'name' => 'accepted_count',
                'label' => 'Одобрили', // Table column heading
                'type' => 'model_function',
                'function_name' => 'getAcceptedCount',
                'orderable' => true,
            ],
            [
                'name' => 'denied_count',
                'label' => 'Отклонили', // Table column heading
                'type' => 'model_function',
                'function_name' => 'getDeniedCount',
                'orderable' => true,
            ],
        ]);

        /**
         * Columns can be defined using the fluent syntax or array syntax:
         * - CRUD::column('price')->type('number');
         * - CRUD::addColumn(['name' => 'price', 'type' => 'number']);
         */
    }

    /**
     * Define what happens when the Create operation is loaded.
     *
     * @see https://backpackforlaravel.com/docs/crud-operation-create
     * @return void
     */
    protected function setupCreateOperation()
    {
        CRUD::setValidation(SuggestionRequest::class);

        CRUD::addFields([
            [
                // 1-n relationship
                'type' => 'select2',
                'label'=>'Автор',
                'name' => 'author_id', // the column that contains the ID of that connected entity;
                'entity' => 'author', // the method that defines the relationship in your Model
                'attribute' => 'full_name', // foreign key attribute that is shown to user
                'model' => "App\Models\User", // foreign key model
                'tab' => 'общая информация',
                'allows_null' => false
            ],
            [
                // 1-n relationship
                'type' => 'select2',
                'label'=>'Направление деятельности',
                'name' => 'topic_id', // the column that contains the ID of that connected entity;
                'entity' => 'topic', // the method that defines the relationship in your Model
                'attribute' => 'title', // foreign key attribute that is shown to user
                'model' => "App\Models\Topic", // foreign key model
                'tab' => 'общая информация',
                'allows_null' => false
            ],
            [
                // 1-n relationship
                'type' => 'text',
                'name' => 'title', // the column that contains the ID of that connected entity;
                'tab' => 'общая информация',
                'label'=>'Название',
            ],
            [
                // 1-n relationship
                'type' => 'textarea',
                'name' => 'existing_solution_text', // the column that contains the ID of that connected entity;
                'tab' => 'что есть',
                'label'=>'Как сейчас(текст)',
            ],
            [
                // 1-n relationship
                'type' => 'browse',
                'label'=>'Как сейчас(картинка)',
                'name' => 'existing_solution_image', // the column that contains the ID of that connected entity;
                'tab' => 'что есть'
            ],
            [
                // 1-n relationship
                'type' => 'browse',
                'label'=>'Как сейчас(видео)',
                'name' => 'existing_solution_video', // the column that contains the ID of that connected entity;
                'tab' => 'что есть'
            ],
            [
                // 1-n relationship
                'type' => 'textarea',
                'label'=>'Как будет(текст)',
                'name' => 'proposed_solution_text', // the column that contains the ID of that connected entity;
                'tab' => 'мое предложение'
            ],
            [
                // 1-n relationship
                'type' => 'browse',
                'label'=>'Как будет(картинка)',
                'tab' => 'мое предложение',
                'name' => 'proposed_solution_image', // the column that contains the ID of that connected entity;
            ],
            [
                // 1-n relationship
                'type' => 'browse',
                'label'=>'Как будет(видео)',
                'tab' => 'мое предложение',
                'name' => 'proposed_solution_video', // the column that contains the ID of that connected entity;
            ],
            [
                'name' => 'positive_effect',
                'label'=>'Положительный эффект',
                'type' => 'textarea',
                'tab' => 'мое предложение'
            ],

        ]);

    }

    /**
     * Define what happens when the Update operation is loaded.
     *
     * @see https://backpackforlaravel.com/docs/crud-operation-update
     * @return void
     */
    protected function setupUpdateOperation()
    {
        $this->setupCreateOperation();
        CRUD::addField([
            // 1-n relationship
            'type' => 'select2',
            'name' => 'status_id', // the column that contains the ID of that connected entity;
            'entity' => 'statuss', // the method that defines the relationship in your Model
            'attribute' => 'title', // foreign key attribute that is shown to user
            'model' => "App\Models\Status", // foreign key model
            'tab' => 'общая информация',
            'allows_null' => false
        ]);
    }

    protected function setupShowOperation()
    {
        CRUD::addColumns([
            [
                // 1-n relationship
                'type' => 'select',
                'label'=>'Автор',
                'name' => 'author_id', // the column that contains the ID of that connected entity;
                'entity' => 'author', // the method that defines the relationship in your Model
                'attribute' => 'full_name', // foreign key attribute that is shown to user
                'model' => "App\Models\User", // foreign key model
            ],
            [
                // 1-n relationship
                'type' => 'select',
                'label'=>'Направление деятельности',
                'name' => 'topic_id', // the column that contains the ID of that connected entity;
                'entity' => 'topic', // the method that defines the relationship in your Model
                'attribute' => 'title', // foreign key attribute that is shown to user
                'model' => "App\Models\Topic", // foreign key model
            ],
            [
                // 1-n relationship
                'type' => 'text',
                'name' => 'title',
                'label'=>'Название',
            ],
            [
                // 1-n relationship
                'type' => 'text',
                'name' => 'existing_solution_text',
                'label'=>'Как есть(текст)',
                ],
            [
                // 1-n relationship
                'type' => 'text',
                'wrapper' => [
                    'href' => function ($crud, $column, $entry, $related_key) {
                        return is_null($entry->existing_solution_image) ? "#" : url($entry->existing_solution_image);
                    },
                    'target' => '_blank',
                ],
                'label'=>'Как есть(картинка)',
                'name' => 'existing_solution_image', // the column that contains the ID of that connected entity;
            ],
            [
                // 1-n relationship
                'type' => 'text',
                'wrapper' => [
                    'href' => function ($crud, $column, $entry, $related_key) {
                        return is_null($entry->existing_solution_video) ? "#" : url($entry->existing_solution_video);

                    },
                    'target' => '_blank',
                ],
                'label'=>'Как есть(видео)',
                'name' => 'existing_solution_video', // the column that contains the ID of that connected entity;
            ],
            [
                // 1-n relationship
                'type' => 'text',
                'name' => 'proposed_solution_text',
                'label'=>'Как будет(текст)',
            ],
            [
                // 1-n relationship
                'type' => 'text',
                'wrapper' => [
                    'href' => function ($crud, $column, $entry, $related_key) {
                        return is_null($entry->proposed_solution_image) ? "#" : url($entry->proposed_solution_image);

                    },
                ],
                'label'=>'Как будет(картинка)',
                'name' => 'proposed_solution_image', // the column that contains the ID of that connected entity;
            ],
            [
                // 1-n relationship
                'type' => 'text',
                'wrapper' => [
                    'href' => function ($crud, $column, $entry, $related_key) {
                        return is_null($entry->proposed_solution_video) ? "#" : url($entry->proposed_solution_video);
                    },
                ],
                'label'=>'Как будет(видео)',
                'name' => 'proposed_solution_video', // the column that contains the ID of that connected entity;
            ],
            [
                'name' => 'positive_effect',
                'type' => 'text',
                'label'=>'Положительный эффект',
            ],
            [
                // 1-n relationship
                'type' => 'select',
                'label'=>'Статус',
                'name' => 'status_id', // the column that contains the ID of that connected entity;
                'entity' => 'statuss', // the method that defines the relationship in your Model
                'attribute' => 'title', // foreign key attribute that is shown to user
                'model' => "App\Models\Status", // foreign key model
            ],
        ]);
    }

    public function store()
    {
        $this->crud->hasAccessOrFail('create');

        // execute the FormRequest authorization and validation, if one is required
        $request = $this->crud->validateRequest();

        $data = request()->all();
        $data['status_id'] = 1;
        if (!is_null(request()->get('existing_solution_image'))) {
            $data['existing_solution_image'] = env('APP_URL') . '/' . request()->get('existing_solution_image');
        }
        if (!is_null(request()->get('existing_solution_video'))) {
            $data['existing_solution_video'] = env('APP_URL') . '/' . request()->get('existing_solution_video');
        }
        if (!is_null(request()->get('proposed_solution_image'))) {
            $data['proposed_solution_image'] = env('APP_URL') . '/' . request()->get('proposed_solution_image');

        }
        if (!is_null(request()->get('proposed_solution_video'))) {
            $data['proposed_solution_video'] = env('APP_URL') . '/' . request()->get('proposed_solution_video');
        }
        // insert item in the db
        $item = $this->crud->create($data);
        $this->data['entry'] = $this->crud->entry = $item;

        // show a success message
        \Alert::success(trans('backpack::crud.insert_success'))->flash();

        // save the redirect choice for next time
        $this->crud->setSaveAction();

        return $this->crud->performSaveAction($item->getKey());
    }

    public function update()
    {
        $this->crud->hasAccessOrFail('update');

        // execute the FormRequest authorization and validation, if one is required
        $request = $this->crud->validateRequest();
        // update the row in the db

        $data = request()->all();
        $sug = Suggestion::find(request()->get('id'));

        if (!is_null(request()->get('existing_solution_image'))) {
            if (request()->get('existing_solution_image') != $sug->existing_solution_image){
                $data['existing_solution_image'] = env('APP_URL') . '/' . request()->get('existing_solution_image');
            }
        }
        if (!is_null(request()->get('existing_solution_video'))) {
            if (request()->get('existing_solution_video') != $sug->existing_solution_video){
                $data['existing_solution_video'] = env('APP_URL') . '/' . request()->get('existing_solution_video');
            }
        }
        if (!is_null(request()->get('proposed_solution_image'))) {
            if (request()->get('proposed_solution_image') != $sug->proposed_solution_image){
                $data['proposed_solution_image'] = env('APP_URL') . '/' . request()->get('proposed_solution_image');
            }

        }
        if (!is_null(request()->get('proposed_solution_video'))) {
            if (request()->get('proposed_solution_video') != $sug->proposed_solution_video){
                $data['proposed_solution_video'] = env('APP_URL') . '/' . request()->get('proposed_solution_video');
            }
        }
        $item = $this->crud->update($request->get($this->crud->model->getKeyName()),
            $data);
        $this->data['entry'] = $this->crud->entry = $item;

        // show a success message
        \Alert::success(trans('backpack::crud.update_success'))->flash();

        // save the redirect choice for next time
        $this->crud->setSaveAction();

        return $this->crud->performSaveAction($item->getKey());
    }

}
