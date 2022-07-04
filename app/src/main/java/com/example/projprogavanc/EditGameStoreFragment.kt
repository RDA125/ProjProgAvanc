package com.example.projprogavanc

import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.example.projprogavanc.databinding.FragmentEditGameStoreBinding

class EditGameStoreFragment : Fragment(),  LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding:FragmentEditGameStoreBinding? = null

    private val binding get() = _binding!!

    private var gameStore :GameStore? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        _binding = FragmentEditGameStoreBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.currMenuId = R.menu.edit_menu

        if(arguments != null){
            gameStore = EditGameStoreFragmentArgs.fromBundle(arguments!!).gamestore

            if(gameStore != null){
                binding.editTextPrice.setText(gameStore!!.preco.toString())

            }

        }

        LoaderManager.getInstance(this).initLoader(ID_LOADER_GAME, null, this)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_STORE, null, this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ID_LOADER_GAME = 0
        const val ID_LOADER_STORE = 1
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

        if(id == ID_LOADER_GAME){
            loader = CursorLoader(
                requireContext(),
                ContentProviderGameStore.GAMES_ADDRESS,
                TDBGames.ALL_COLUMNS,
                null,
                null,
                TDBGames.C_NAME
            )

        }else if(id == ID_LOADER_STORE){

            loader = CursorLoader(
                requireContext(),
                ContentProviderGameStore.STORES_ADDRESS,
                TDBStores.ALL_COLUMNS,
                null,
                null,
                TDBStores.C_NAME

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

        if(loader.id == ID_LOADER_GAME){
            val adapterGame = SimpleCursorAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                data,
                arrayOf(TDBGames.C_NAME),
                intArrayOf(android.R.id.text1),
                0
            )

            binding.spinnerGame.adapter = adapterGame

            updateSelectedGame()

        }else if(loader.id == ID_LOADER_STORE){
            val adapterStore = SimpleCursorAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                data,
                arrayOf(TDBStores.C_NAME),
                intArrayOf(android.R.id.text1),
                0
            )

            binding.spinnerStore.adapter = adapterStore
            UpdateSelectedStore()

        }


    }

    private fun updateSelectedGame() {
        if(gameStore == null) return

        val gameId = gameStore!!.game.id

        val lastGame = binding.spinnerGame.count - 1

        for (i in 0..lastGame){
            if(binding.spinnerGame.getItemIdAtPosition(i) == gameId) {
                binding.spinnerGame.setSelection(i)
                return
            }
        }
    }

    private fun UpdateSelectedStore() {
        if(gameStore == null) return

        val storeId = gameStore!!.store.id

        val lastStore = binding.spinnerStore.count - 1

        for (i in 0..lastStore){
            if(binding.spinnerStore.getItemIdAtPosition(i) == storeId) {
                binding.spinnerStore.setSelection(i)
                return
            }
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
        binding.spinnerGame.adapter = null
        binding.spinnerStore.adapter = null
    }

    fun processOptionMenu(item: MenuItem): Boolean =
        when(item.itemId){
            R.id.action_save ->{
                save()
                true
            }
            R.id.action_cancel ->{
                backtoGameStoreList()
                true
            }
            else -> false
        }



    private fun save() {

        val gameId = binding.spinnerGame.selectedItemId
        if(gameId == Spinner.INVALID_ROW_ID){
            binding.textViewGame.error = getString(R.string.GameSpin_error)
            binding.spinnerGame.requestFocus()
            return

        }

        val storeId = binding.spinnerStore.selectedItemId
        if(storeId == Spinner.INVALID_ROW_ID){
            binding.textViewStore.error = getString(R.string.StoreSpin_error)
            binding.spinnerStore.requestFocus()
            return

        }

        val price = binding.editTextPrice.text.toString()
        if(price.isBlank()){
            binding.editTextPrice.error = getString(R.string.Price_error)
            binding.editTextPrice.requestFocus()
            return

        }

        val savedGameStore =
            if(gameStore == null){
                insertGameStore(gameId,storeId,price)

            }else{
                editGameStore(gameId,storeId,price)
            }


        if(savedGameStore){

            Toast.makeText(requireContext(), R.string.SaveGameStore_success, Toast.LENGTH_LONG).show()
            backtoGameStoreList()

        }else{
            Toast.makeText(requireContext(), R.string.General_error, Toast.LENGTH_LONG).show()

        }
    }

    private fun editGameStore(gameId: Long, storeId: Long, price: String): Boolean {
        val game = Uri.withAppendedPath(ContentProviderGameStore.GAMES_ADDRESS, gameId.toString())
        val store =Uri.withAppendedPath(ContentProviderGameStore.STORES_ADDRESS, storeId.toString())

        val SelectedGame = requireActivity().contentResolver.query(game,TDBGames.ALL_COLUMNS,"${TDBGames.C_ID} = ?",arrayOf(gameId.toString()),null)
        val SelectedStore = requireActivity().contentResolver.query(store,TDBStores.ALL_COLUMNS,"${TDBStores.C_ID} = ?",arrayOf(storeId.toString()),null)

        if((SelectedGame == null) || (SelectedStore == null)) return false
        assert(SelectedGame.moveToNext())
        assert(SelectedStore.moveToNext())

        val gameStore = GameStore(price.toDouble(),Game.fromCursor(SelectedGame),Store.fromCursor(SelectedStore))
        val gameStoreAddress = Uri.withAppendedPath(ContentProviderGameStore.GAME_STORES_ADDRESS,"${this.gameStore!!.id}")

        val UpdatedEntries = requireActivity().contentResolver.update(gameStoreAddress, gameStore.toContentValues(),null,null)

        return UpdatedEntries == 1

    }

    private fun insertGameStore(
        gameId: Long,
        storeId: Long,
        price: String
    ):Boolean {

        val game = Uri.withAppendedPath(ContentProviderGameStore.GAMES_ADDRESS, gameId.toString())
        val store =Uri.withAppendedPath(ContentProviderGameStore.STORES_ADDRESS, storeId.toString())

        val SelectedGame = requireActivity().contentResolver.query(game,TDBGames.ALL_COLUMNS,"${TDBGames.C_ID} = ?",arrayOf(gameId.toString()),null)
        val SelectedStore = requireActivity().contentResolver.query(store,TDBStores.ALL_COLUMNS,"${TDBStores.C_ID} = ?",arrayOf(storeId.toString()),null)

        if((SelectedGame == null) || (SelectedStore == null)) return false

        //TODO("Verify if a Game Store with same data already exists in the table if so return false")

        assert(SelectedGame.moveToNext())
        assert(SelectedStore.moveToNext())

        val gameStore = GameStore(price.toDouble(),Game.fromCursor(SelectedGame),Store.fromCursor(SelectedStore))
        val insertedGameStoreAddress = requireActivity().contentResolver.insert(ContentProviderGameStore.GAME_STORES_ADDRESS, gameStore.toContentValues()) ?: return false

        gameStore.id = ContentUris.parseId(insertedGameStoreAddress)

        return true

    }

    private fun backtoGameStoreList() {
        (requireActivity() as MainActivity).updateTitle(getString(R.string.Wishlist_title))
        findNavController().navigate(R.id.action_EditGameStoreFragment_to_wishlist)
    }

}