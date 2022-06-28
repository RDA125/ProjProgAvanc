package com.example.projprogavanc

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.example.projprogavanc.databinding.FragmentInsertGameStoreBinding

class InsertGameStoreFragment : Fragment(),  LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding:FragmentInsertGameStoreBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        _binding = FragmentInsertGameStoreBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoaderManager.getInstance(this).initLoader(ID_LOADER_GAMETYPE, null, this)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_STORETYPE, null, this)

        val activity = activity as MainActivity
        activity.fragment = this
        //activity.currMenuId = R.menu.list_menu

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ID_LOADER_GAMETYPE = 0
        const val ID_LOADER_STORETYPE = 1
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        var loader: Loader<Cursor>? = null

        if(id == ID_LOADER_GAMETYPE){
            loader = CursorLoader(
                requireContext(),
                ContentProviderGameStore.GAMETYPES_ADDRESS,
                TDBGameTypes.ALL_COLUMNS,
                null,
                null,
                "${TDBGameTypes.C_TYPE}"
            )

        }else if(id == ID_LOADER_STORETYPE){

            loader = CursorLoader(
                requireContext(),
                ContentProviderGameStore.STORETYPES_ADDRESS,
                TDBStoreTypes.ALL_COLUMNS,
                null,
                null,
                "${TDBStoreTypes.C_TYPE}"

            )

        }

        return loader!!
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {

        if(loader.id == ID_LOADER_GAMETYPE){
            val adapterGameType = SimpleCursorAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                data,
                arrayOf(TDBGameTypes.C_TYPE),
                intArrayOf(android.R.id.text1),
                0
            )

            binding.spinnerGameType.adapter = adapterGameType

        }else if(loader.id == ID_LOADER_STORETYPE){
            val adapterStoreType = SimpleCursorAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                data,
                arrayOf(TDBStoreTypes.C_TYPE),
                intArrayOf(android.R.id.text1),
                0
            )

            binding.spinnerStoreType.adapter = adapterStoreType
        }


    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    override fun onLoaderReset(loader: Loader<Cursor>) {
        if(_binding == null) return
        binding.spinnerGameType.adapter = null
        binding.spinnerStoreType.adapter = null
    }
}